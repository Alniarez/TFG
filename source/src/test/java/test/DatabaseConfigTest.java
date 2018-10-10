package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DatabaseConfigTest {

    public static Sql2o getSql2oConnection() {
        return createSql2oConnection();
    }

    private static Sql2o createSql2oConnection() {
        Sql2o sql2o = new Sql2o(SQL_URL, DB_USER, DB_PASS);

        // Map<Nombre Columna, Nombre variable>
        Map<String, String> colMaps = new HashMap<String, String>();
        colMaps.put("user_id", "userId");
        colMaps.put("thread_id", "threadId");
        colMaps.put("subject_id", "subjectId");
        colMaps.put("psw", "password");

        sql2o.setDefaultColumnMappings(colMaps);

        return sql2o;
    }

    public static void init() throws IOException {
        createInMemoryDatabase();
    }

    private static void createInMemoryDatabase() {
        try (Connection con = getSql2oConnection().open()) {
            con.createQuery(create_users).executeUpdate();
            con.createQuery(create_subjects).executeUpdate();
            con.createQuery(create_threads).executeUpdate();
            con.createQuery(create_messages).executeUpdate();
            con.createQuery(create_assignments).executeUpdate();
        }
    }

    private static String SQL_URL = "jdbc:hsqldb:mem:testDatabase", DB_USER = "sa", DB_PASS = "",
            create_users = "create table users (id integer identity primary key, name varchar(255) not null, email varchar(255) not null, admin boolean not null, login varchar(255) not null unique, psw varchar(255) not null, active boolean default true);",
            create_subjects = "create table subjects( id integer identity primary key, code varchar(255) unique not null);",
            create_threads = "create table threads( id integer identity primary key, name varchar(255) not null, email varchar(255) not null, subject_id integer not null, date timestamp not null,  open boolean default true, text varchar(32768) not null, topic varchar(255) not null, foreign key (subject_id) references subjects(id));",
            create_messages = "create table messages( id integer identity primary key, thread_id integer not null, text varchar(32768) not null, date timestamp not null, user_id integer not null, foreign key (user_id) references users(id), foreign key (thread_id) references threads(id));",
            create_assignments = "create table assignments( user_id integer, subject_id integer, primary key(user_id, subject_id), foreign key (user_id) references users(id), foreign key (subject_id) references subjects(id));";

}
