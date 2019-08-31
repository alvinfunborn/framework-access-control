#### abac,rbac权限控制框架
1. 支持角色继承,数据组,行为组
2. 支持动态角色和静态角色

##### usage
1. 用户与角色需要额外维护
2. 权限控制规则默认驻留内存, 可自定义RuleRepository实现
3. 角色继承关系默认驻留内存, 可自定义RoleHierarchyRepository实现
4. 数据组关系默认驻留内存, 可自定义DataGroupRepository实现
5. 行为组关系默认驻留内存, 可自定义ActionGroupRepository实现
6. 若使用动态角色需要实现DynamicRoleDefiner

###### example
```
@Test
void testStaticRoleEnforce() {
    // role
    String ai = "ai";
    String healthyPeople = "healthyPeople";
    String disabledPeople = "disabledPeople";
    String people = "people";

    // data
    String car = "car";
    String c1Car = "c1Car";
    String c3Car = "c3Car";
    String boat = "boat";
    String conveyance = "conveyance";

    // action
    String board = "board";
    String drive = "drive";
    String dayDrive = "dayDrive";
    String nightDrive = "nightDrive";

    StaticRoleEnforcer staticRoleEnforcer = new StandardEnforcerBuilder().buildStaticRoleEnforcer();
    // establish role hierarchy
    staticRoleEnforcer.addRoleHierarchy(new Hierarchy(healthyPeople, people));
    staticRoleEnforcer.addRoleHierarchy(new Hierarchy(disabledPeople, people));
    // establish data group relationship
    staticRoleEnforcer.addDataGroup(new Group(car, c1Car, c3Car));
    staticRoleEnforcer.addDataGroup(new Group(conveyance, car, boat));
    // establish action group relationship
    staticRoleEnforcer.addActionGroup(new Group(drive, dayDrive, nightDrive));

    // condition: need specific license to drive a car
    Condition requireLicense = new Condition("requireLicense") {
        @Override
        public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
            TestStaticRoleSubject mySubject = (TestStaticRoleSubject) subject;
            TestResource testResource = (TestResource) resource;
            if (testResource.getLicense() == null || mySubject.getLicense() != null && mySubject.getLicense().contains(testResource.getLicense())) {
                return Result.ofPermit();
            }
            return Result.ofDeny("has no license");
        }
    };
    // condition: cannot drive a car after 70 years old
    Condition ageLte70 = new Condition("ageLte70") {
        @Override
        public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
            if (((TestStaticRoleSubject) subject).getAge() > 70) {
                return Result.ofDeny("age greater than 70");
            }
            return Result.ofPermit();
        }
    };
    // condition: cannot drive a car before 18 years old
    Condition ageGte18 = new Condition("ageGte18") {
        @Override
        public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
            if (((TestStaticRoleSubject) subject).getAge() < 18) {
                return Result.ofDeny("age less than 18");
            }
            return Result.ofPermit();
        }
    };
    // combined condition
    Condition licenseAndAge = Condition.and("license and age", requireLicense, ageLte70);
    Condition ageGte18Lte70 = Condition.and("age gte18 and lte70", ageGte18, ageLte70);

    // permission rules
    staticRoleEnforcer.addPolicy(Policy.ofPermit("licensed healthyPeople of a right age may drive car", healthyPeople, car, drive, licenseAndAge));
    staticRoleEnforcer.addPolicy(Policy.ofPermit("healthyPeople of a right age may drive boat", healthyPeople, boat, drive, ageGte18Lte70));
    staticRoleEnforcer.addPolicy(Policy.ofPermit("people may board conveyance", people, conveyance, board));
    staticRoleEnforcer.addPolicy(Policy.ofPermit("ai may drive conveyance", ai, conveyance, drive));
    staticRoleEnforcer.addPolicy(Policy.ofDeny("ai may not drive conveyance at night", ai, conveyance, nightDrive, Collections.singletonMap(ResultCode.DENY, "cannot drive at night")));

    // create test resources
    TestResource sport = new TestResource(c1Car, "c1_car_license");
    TestResource truck = new TestResource(c3Car, "c3_car_license");
    TestResource yacht = new TestResource(boat, null);
    // create test subjects
    TestStaticRoleSubject sam = new TestStaticRoleSubject(healthyPeople, 71, Arrays.asList("c1_car_license", "c3_car_license"));
    TestStaticRoleSubject mike = new TestStaticRoleSubject(healthyPeople, 30, Collections.singletonList("c1_car_license"));
    TestStaticRoleSubject will = new TestStaticRoleSubject(healthyPeople, 17, null);
    TestStaticRoleSubject me = new TestStaticRoleSubject(disabledPeople, 24, Collections.singletonList("c1_car_license"));
    TestStaticRoleSubject tesla = new TestStaticRoleSubject(ai, 0, null);

    // execute
    System.out.println("sam drive sport. " + staticRoleEnforcer.enforce(sam, sport, drive));
    System.out.println("sam drive yacht. " + staticRoleEnforcer.enforce(sam, yacht, drive));
    System.out.println("mike board truck. " + staticRoleEnforcer.enforce(mike, truck, board));
    System.out.println("mike drive truck. " + staticRoleEnforcer.enforce(mike, truck, drive));
    System.out.println("mike drive sport at night. " + staticRoleEnforcer.enforce(mike, sport, nightDrive));
    System.out.println("will drive sport at day. " + staticRoleEnforcer.enforce(will, sport, dayDrive));
    System.out.println("will drive yacht at night. " + staticRoleEnforcer.enforce(will, yacht, nightDrive));
    System.out.println("will board yacht. " + staticRoleEnforcer.enforce(will, yacht, board));
    System.out.println("me drive sport. " + staticRoleEnforcer.enforce(me, sport, drive));
    System.out.println("me board sport. " + staticRoleEnforcer.enforce(me, sport, board));
    System.out.println("tesla drive truck. " + staticRoleEnforcer.enforce(tesla, truck, drive));
    System.out.println("tesla drive yacht at night. " + staticRoleEnforcer.enforce(tesla, yacht, nightDrive));
    System.out.println("ai board sport. " + staticRoleEnforcer.enforce(tesla, sport, board));
}

@Test
void testDynamicRoleEnforce() {
    // dynamic role. a seasickness man is not healthy in boat situation
    String healthyPeople = "healthyPeople";
    // data
    String car = "car";
    String boat = "boat";
    // action
    String drive = "drive";

    DynamicRoleEnforcer staticRoleEnforcer = new StandardEnforcerBuilder().buildDynamicRoleEnforcer(new TestDynamicRoleDefiner());
    // permission rules
    staticRoleEnforcer.addPolicy(Policy.ofPermit("healthyPeople may drive car", healthyPeople, car, drive));
    staticRoleEnforcer.addPolicy(Policy.ofPermit("healthyPeople may drive boat", healthyPeople, boat, drive));

    // create test resource
    TestResource sport = new TestResource(car, null);
    TestResource yacht = new TestResource(boat, null);
    // create test subject
    TestDynamicRoleSubject sam = new TestDynamicRoleSubject(false, false);
    TestDynamicRoleSubject mike = new TestDynamicRoleSubject(true, false);
    TestDynamicRoleSubject will = new TestDynamicRoleSubject(false, true);

    // execute
    System.out.println("sam drive sport. " + staticRoleEnforcer.enforce(sam, sport, drive));
    System.out.println("sam drive yacht. " + staticRoleEnforcer.enforce(sam, yacht, drive));
    System.out.println("mike drive sport. " + staticRoleEnforcer.enforce(mike, sport, drive));
    System.out.println("mike drive yacht. " + staticRoleEnforcer.enforce(mike, yacht, drive));
    System.out.println("will drive sport. " + staticRoleEnforcer.enforce(will, sport, drive));
    System.out.println("will drive yacht. " + staticRoleEnforcer.enforce(will, yacht, drive));
}

class TestStaticRoleSubject extends StaticRoleSubject {
    private int age;
    private List<String> license;

    public TestStaticRoleSubject(String role, int age, List<String> license) {
        super(role);
        this.age = age;
        this.license = license;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getLicense() {
        return license;
    }

    public void setLicense(List<String> license) {
        this.license = license;
    }
}

class TestResource extends Resource {
    private String license;

    public TestResource(String data, String license) {
        super(data);
        this.license = license;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}

class TestDynamicRoleSubject extends DynamicRoleSubject {
    private boolean parachromatoblepsia;
    private boolean seasickness;

    public TestDynamicRoleSubject(boolean parachromatoblepsia, boolean seasickness) {
        this.parachromatoblepsia = parachromatoblepsia;
        this.seasickness = seasickness;
    }

    public boolean isParachromatoblepsia() {
        return parachromatoblepsia;
    }

    public void setParachromatoblepsia(boolean parachromatoblepsia) {
        this.parachromatoblepsia = parachromatoblepsia;
    }

    public boolean isSeasickness() {
        return seasickness;
    }

    public void setSeasickness(boolean seasickness) {
        this.seasickness = seasickness;
    }
}

class TestDynamicRoleDefiner implements DynamicRoleDefiner {
    @Override
    public <S extends DynamicRoleSubject, R extends Resource> S define(S subject, R resource) {
        TestResource testResource = (TestResource) resource;
        if (((TestDynamicRoleSubject) subject).isSeasickness() && testResource.getData().equals("boat")
                || ((TestDynamicRoleSubject) subject).isParachromatoblepsia() && testResource.getData().equals("car")) {
            subject.setRole("disabledPeople");
        } else {
            subject.setRole("healthyPeople");
        }
        return subject;
    }
}
```
