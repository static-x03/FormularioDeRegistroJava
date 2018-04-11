/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class BasesDeDatos {

    private static Connection conexion;

    public void conecion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/basesdedatosproyecto?user=root&password=&useSSL=false");
            System.out.println("conexion Exitosa!!");
        } catch (SQLException ex) {
            Logger.getLogger(BasesDeDatos.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BasesDeDatos.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void insertDataUsuario(String id, String nombres, String apellido, String email) {
        try {
            Statement sentencia = conexion.createStatement();
            String consulta = "insert into usuario values (" + id + ", '" + nombres + "', '" + apellido + "', '" + email + "')";
            System.err.println(consulta);
            int resultados = sentencia.executeUpdate(consulta);
            System.err.println("resultado = " + resultados);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");

        }

    }

    public void closeConnection() {
        try {

            conexion.close();
            System.out.println("SE HA FINALIZADO LA CONEXION CON EL SERVIDOR ");
        } catch (SQLException ex) {
            Logger.getLogger(BasesDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setFilas(DefaultTableModel modelo) {

        try {
            String sql = "select id_usuario, nombres, apellido, email from usuario";
            PreparedStatement us = conexion.prepareStatement(sql);
            ResultSet res = us.executeQuery();
            Object datos[] = new Object[4];
            while (res.next()) {
                for (int i = 0; i < 4; i++) {
                    datos[i] = res.getObject(i + 1);
                }
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BasesDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteRecord(String table_name, String id) {
        try {
            String Query = "DELETE FROM " + table_name + " WHERE id_usuario = \"" + id + "\"";
            Statement st = conexion.createStatement();
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error borrando el registro especificado");
        }

    }

    public String[] getValues(String table_name, String id) {
        String[] datos = new String[4];
        try {
            Statement sentencia = conexion.createStatement();

            String cad = "select * from usuario where id_usuario =  " + id;

            System.err.println(cad);
            ResultSet resultados = sentencia.executeQuery(cad);
            resultados.next();
            datos[0] = resultados.getString(1);
            datos[1] = resultados.getString(2);
            datos[2] = resultados.getString(3);
            datos[3] = resultados.getString(4);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la adquisicion de datos");
        }
        return datos;
    }
    public void update(String id, String nombres, String apellido, String email) {
        try {
            Statement sentencia = conexion.createStatement();
            String consulta = "update usuario set nombres = '" + nombres + "', apellido = '"
                    + apellido + "', email = '" + email + "' where id_usuario = " + id;
            System.err.println(consulta);
            int resultado = sentencia.executeUpdate(consulta);
            System.err.println("resultados = " + resultado);

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");
        } 
    }
}
