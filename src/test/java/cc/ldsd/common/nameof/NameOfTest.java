package cc.ldsd.common.nameof;

import cc.ldsd.common.nameof.bean.Manager;
import cc.ldsd.common.nameof.bean.User;
import cc.ldsd.common.namof.NameOf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

/**
 * Created by @author songjhh
 */
@Testable
class NameOfTest {

    @Test
    void test01() {
        Assertions.assertEquals("_id", NameOf.init(User.class).field(User::getId).str());
        Assertions.assertEquals("id", NameOf.init(User.class, false).field(User::getId).str());
        Assertions.assertEquals("name", NameOf.init(User.class).field(User::getName).str());
        Assertions.assertEquals("age", NameOf.init(User.class).field(User::getAge).str());
    }

    @Test
    void test02() {
        Assertions.assertEquals("_id", NameOf.init(Manager.class).field(Manager::getId).str());
        Assertions.assertEquals("id", NameOf.init(Manager.class, false).field(Manager::getId).str());
        Assertions.assertEquals("name", NameOf.init(Manager.class).field(Manager::getName).str());
        Assertions.assertEquals("age", NameOf.init(Manager.class).field(Manager::getAge).str());
        Assertions.assertEquals("post", NameOf.init(Manager.class).field(Manager::getPost).str());
    }

    @Test
    void test03() {
        Assertions.assertArrayEquals(new String[]{"age", "post"},
                NameOf.init(Manager.class).field(Manager::getAge).field(Manager::getPost).array());
    }

    @Test
    void test04() {
        Assertions.assertEquals("age.post",
                NameOf.init(Manager.class).field(Manager::getAge).any(".").field(Manager::getPost).str());
    }
}