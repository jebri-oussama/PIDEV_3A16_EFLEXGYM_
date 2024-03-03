package gestion_user;



import utils.DataSource;


public class Main {
    public static void main(String[] args) {
        DataSource ds1 = DataSource.getInstance();
        System.out.println(ds1);

    }
}