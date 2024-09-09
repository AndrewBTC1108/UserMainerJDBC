package org.catc.java.jdbc;

import org.catc.java.jdbc.model.User;
import org.catc.java.jdbc.service.Service;
import org.catc.java.jdbc.service.ServiceCatalog;

import javax.swing.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserMaintainer {
    public static void main(String[] args) throws SQLException {

        Service service = new ServiceCatalog();

        int opcionIndice = 0;
        do {
            Map<String, Integer> operaciones = new HashMap();
            operaciones.put("Update", 1);
            operaciones.put("Delete", 2);
            operaciones.put("Add", 3);
            operaciones.put("List", 4);
            operaciones.put("Exit", 5);

            //become the key sets from hasmap called operaciones, to array
            Object[] opArreglo = operaciones.keySet().toArray();
            Object opcion = JOptionPane.showInputDialog(null,
                    "Select an operation", "User Maintainer", JOptionPane.INFORMATION_MESSAGE, null, opArreglo, opArreglo[0]);

            if (opcion == null) {
                JOptionPane.showMessageDialog(null, "You must select an operation");
            } else {
                opcionIndice = operaciones.get(opcion.toString());//select option from opcion JOptionPane
                Long id;
                String username;
                String password;
                String email;

                switch (opcionIndice) {
                    case 1 -> {
                        id = Long.valueOf(JOptionPane.showInputDialog(null, "Enter the user id to update"));//put ID

                        User user = service.byId(id);//find ID with byId method
                        if (user != null) {
                            username = JOptionPane.showInputDialog(null, "Enter username:", user.getUsername());
                            password = JOptionPane.showInputDialog(null, "Enter password:", user.getPassword());
                            email = JOptionPane.showInputDialog(null, "Enter email:", user.getEmail());

                            user.setUsername(username);
                            user.setPassword(password);
                            user.setEmail(email);

                            service.save(user);

                            JOptionPane.showMessageDialog(null, "User updated correctly");
                        } else {
                            JOptionPane.showMessageDialog(null, "No user id in the database");
                        }
                    }
                    case 2 -> {
                        id = Long.valueOf(JOptionPane.showInputDialog(null, "Enter the id user to delete:"));
                        service.delete(id);
                        JOptionPane.showMessageDialog(null, "User successfully deleted");
                    }
                    case 3 -> {
                        username = JOptionPane.showInputDialog(null, "Enter the username to create new user");
                        password = JOptionPane.showInputDialog(null, "Enter password:");
                        email = JOptionPane.showInputDialog(null, "Enter email:");

                        User user = new User();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);

                        service.save(user);
                        JOptionPane.showMessageDialog(null, "user added correctly");
                    }
                    case 4 -> {
                        service.list().forEach(System.out::println);
                    }
                }

            }

        } while (opcionIndice != 5);

        JOptionPane.showMessageDialog(null, "You have left correctly");

    }
}

