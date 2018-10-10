package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import config.DatabaseConfig;
import model.Assignment;
import model.Message;
import model.Subject;
import model.User;
import model.Thread;
import persistence.exception.PersistenceException;
import util.PasswordUtil;

public class Test {

    @org.junit.BeforeClass
    public static void init() {
        try {
            DatabaseConfigTest.init();

            DatabaseConfig.init();

            DatabaseConfig.forceTestingSql2oConnection(DatabaseConfigTest.getSql2oConnection());

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @org.junit.Test
    public void testUsers() {
        assertTrue(new User().all().isEmpty());

        try {
            User user = new User().setAdmin(false).setEmail("Email@dom.com").setName("Nice dog")
                    .setLogin("dog").setPassword(PasswordUtil.hash("dog"));

            Integer generatedId = (Integer) user.save();
            new User().setId(generatedId);
            user.setName("Bad dog");
            User userDog = new User().setId(generatedId).one();
            assertFalse(user.getName().equals(userDog.getName()));
            User userDog2 = new User().setId(generatedId).one();
            assertTrue(userDog.getName().equals(userDog2.getName()));
            userDog.setName(user.getName()).update();
            userDog = new User().setId(generatedId).one();
            assertTrue(user.getName().equals(userDog.getName()));
            assertFalse(userDog.getName().equals(userDog2.getName()));
            for (User u : new User().all())
                u.delete();
            assertTrue(new User().all().isEmpty());
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }
        User user = new User().setAdmin(false).setEmail("Email@dom.com").setName("Nice dog")
                .setLogin("dog").setPassword(PasswordUtil.hash("dog"));
        Integer id = -1;
        try {
            id = (Integer) user.save();
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }
        User user2 = new User().setAdmin(false).setEmail("Email@dom.com").setName("Nice dog 2")
                .setLogin("dog").setPassword(PasswordUtil.hash("dog"));
        try {
            user2.save();
        } catch (PersistenceException expected) {
            assertTrue(expected != null);
        }
        user2.setLogin("dogdog");
        try {
            user2.save();
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }
        user = user.setId(id).one();
        try {
            user.setLogin("dogdog").update();
        } catch (PersistenceException expected) {
            assertTrue(expected != null);
        }
        Integer updated = -1;
        try {
            updated = user.setLogin("dogodogo").update();
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }
        assertTrue(updated == 1);
    }

    @org.junit.Test
    public void testSubjects() {
        String[] testSubjects = { "Sub1", "Sub2", "Sub3" };
        int[] testSubjectsIds = new int[3];
        for (int i = 0; i < testSubjects.length; i++)
            try {
                testSubjectsIds[i] = (Integer) new Subject().setCode(testSubjects[i]).save();
            } catch (PersistenceException e) {
                fail("Unexpected exception while testing.");
            }

        int totalSubjects = new Subject().all().size();

        try {
            assertTrue(1 == new Subject().setId(testSubjectsIds[0]).setCode("Sub1Mod").update());
            assertTrue("Sub1Mod".equals(new Subject().setId(testSubjectsIds[0]).one().getCode()));
            assertTrue(1 == new Subject().setId(testSubjectsIds[0]).delete());
            assertTrue(null == new Subject().setId(-50).one());
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }

        assertTrue(totalSubjects - 1 == new Subject().all().size());
    }

    @org.junit.Test
    public void testThreads() {
        Integer subjectId = -1;
        try {
            subjectId = (Integer) new Subject().setCode("Number problems").save();
        } catch (PersistenceException e1) {
            fail("Unexpected exception while testing.");
        }

        int totalSubjectsCreated = 25;

        for (int i = 0; i < totalSubjectsCreated; i++) {
            try {
                new Thread().setDate(new Date()).setEmail("mail@domanin.com").setName("John Doe")
                        .setTopic("John Doe tiene un problema con el número " + 0
                                + (int) (Math.random() * 100))
                        .setText("Tengo un problema con algo.").setSubjectId(subjectId).save();
            } catch (PersistenceException e) {

            }
        }
        try {
            int count = 0;
            for (Thread thread : new Thread().all()) {
                count++;
                assertTrue(1 == thread.setText("Es distinto").update());
                assertTrue(1 == thread.delete());
                if (count == 3)
                    break;
            }
            assertFalse(totalSubjectsCreated == new Thread().all().size());
            for (Thread thread : new Thread().all()) {
                thread.delete();
            }
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }
    }

    @org.junit.Test
    public void testMessages() {
        try {
            Integer subjectId = (Integer) new Subject().setCode("New subject").save();
            Integer threadId1 = (Integer) new Thread().setDate(new Date())
                    .setEmail("mail@domanin.com").setName("John Doe")
                    .setTopic("John Doe tiene un problema con el número " + 0
                            + (int) (Math.random() * 100))
                    .setText("Tengo un problema con algo.").setSubjectId(subjectId).save();
            Integer threadId2 = (Integer) new Thread().setDate(new Date())
                    .setEmail("mail@domanin.com").setName("John Doe")
                    .setTopic("John Doe tiene un problema con el número " + 0
                            + (int) (Math.random() * 100))
                    .setText("Tengo un problema con algo.").setSubjectId(subjectId).save();
            Integer userId = (Integer) new User().setAdmin(false).setEmail("Email@dom.com")
                    .setName("Nice dog").setLogin("not the dog login name again")
                    .setPassword(PasswordUtil.hash("dog")).save();
            new Message().setDate(new Date()).setText("text").setUserId(userId)
                    .setThreadId(threadId1).save();
            Integer messageId2 = (Integer) new Message().setDate(new Date()).setText("text")
                    .setUserId(userId).setThreadId(threadId2).save();
            Message message = new Message().setId(messageId2).one();
            assertFalse(null == message);
            int mod = message.setText("text mod").update();
            assertTrue(mod == 1);
            int del = message.delete();
            assertTrue(del == 1);
            assertTrue(new Thread().setId(threadId2).delete() == 1);
            assertTrue(new Thread().setId(threadId2).delete() == 0);
            assertTrue(new Thread().setId(threadId1).delete() == 2);
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }
    }

    @org.junit.Test
    public void testAssignments() {
        User cat = new User().setAdmin(false).setEmail("Email@dom.com").setName("Happy cat")
                .setLogin("cat").setPassword(PasswordUtil.hash("cat"));
        Integer userId = -1;
        Integer subjectId = -1;
        Integer subjectId2 = -1;
        try {
            userId = (Integer) cat.save();
            subjectId = (Integer) new Subject().setCode("Cat stuff").save();
            subjectId2 = (Integer) new Subject().setCode("More cat stuff").save();
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }

        try {
            new Assignment().setSubjectId(subjectId).setUserId(userId).save();
        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }

        try {
            new Assignment().setSubjectId(subjectId).setUserId(userId).save();
        } catch (PersistenceException expected) {
            assertTrue(expected != null);
        }

        try {

            assertTrue(new Assignment().setUserId(userId).setSubjectId(subjectId2).update() == 1);
            assertTrue(new Assignment().setUserId(userId).setSubjectId(subjectId2).one() != null);
            assertTrue(new Assignment().setUserId(userId).setSubjectId(subjectId2).delete() == 1);
            assertTrue(new Assignment().setUserId(userId).setSubjectId(subjectId2).one() == null);

            assertTrue(new Assignment().all().isEmpty());

        } catch (PersistenceException e) {
            fail("Unexpected exception while testing.");
        }

    }

}
