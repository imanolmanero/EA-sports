package Modelo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JornadaDAO {

    public static boolean crearJornada(LocalDate fechaInicio) {
        boolean encontrado = false;

        try {
            BaseDatos.abrirConexion();
            Connection con = BaseDatos.getCon();

            LocalDate fechaFin = fechaInicio.plusWeeks(1);

            String plantilla = "INSERT INTO jornadas (fechaInicio, fechaFin) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(plantilla);

            java.sql.Date fechaInicioSql = java.sql.Date.valueOf(fechaInicio);
            java.sql.Date fechaFinSql = java.sql.Date.valueOf(fechaFin);

            ps.setDate(1, fechaInicioSql);
            ps.setDate(2, fechaFinSql);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                encontrado = true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
        return encontrado;
    }

    public static boolean existeJornada(String numJornada) {
        boolean existe = false;
        try {
            BaseDatos.abrirConexion();
            Connection con = BaseDatos.getCon();

            String plantilla = "SELECT COUNT(*) FROM jornadas WHERE numJornada = ?";
            PreparedStatement ps = con.prepareStatement(plantilla);
            ps.setString(1, numJornada);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;  // Si el count es mayor que 0, significa que ya existe
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
        return existe;
    }

    public static List<String[]> obtenerJornadas() {
        List<String[]> jornadas = new ArrayList<>();

        try{
            BaseDatos.abrirConexion();
            Connection con = BaseDatos.getCon();

            String plantilla = "SELECT fechaInicio,fechaFin FROM jornadas";
            PreparedStatement ps = con.prepareStatement(plantilla);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String fechainicio = rs.getDate("fechainicio").toString();
                String fechafin = rs.getDate("fechafin").toString();
                jornadas.add(new String[]{fechainicio, fechafin});

            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
        return jornadas;
    }

    public static List<String> listaJornadas() {
        List<String> jornadas = new ArrayList<>();

        try{
           BaseDatos.abrirConexion();
           Connection con = BaseDatos.getCon();

           String plantilla = "SELECT numJornada FROM jornadas";
           PreparedStatement ps = con.prepareStatement(plantilla);

           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               jornadas.add(rs.getString(1));
           }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
        return jornadas;
    }

}