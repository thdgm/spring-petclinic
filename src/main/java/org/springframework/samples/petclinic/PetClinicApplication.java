/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.samples.petclinic.owner.Owner;

import jakarta.transaction.Transactional;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 *
 */
@SpringBootApplication
@ImportRuntimeHints(PetClinicRuntimeHints.class)
public class PetClinicApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}

	// Taller 1
	public void Tarea1(Connection conn) throws Exception {
		try {
			System.out.println("TALLER 1");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "petclinic", "petclinic");
			Statement smt = null;
			smt = conn.createStatement();
			String sql = "SELECT * FROM owners";
			final Object[][] table = new String[4][];
			int i = 0;
			if (smt != null) {
				ResultSet res = smt.executeQuery(sql);
				String id = "";
				String fn = "";
				String ln = "";
				String add = "";
				String c = "";
				String tlf = "";
				if (res != null) {
					while (res.next()) {
						id = res.getString("id");
						fn = res.getString("first_name");
						ln = res.getString("last_name");
						add = res.getString("address");
						c = res.getString("city");
						tlf = res.getString("telephone");
						table[i] = new String[] { id.toString(), fn, ln, add, c, tlf };
						System.out.format("|%1$-10s|%2$-20s|%3$-25s|%4$-25s|%5$-25s|%6$-25s\n", table[i]);
					}
				}
			}
		}
		catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	// Taller 2
	public void Tarea2(Connection conn, String ciudad) throws Exception {
		try {
			System.out.println("TALLER 2");
			Scanner lectura = new Scanner(System.in);

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "petclinic", "petclinic");

			System.out.println("Ingrese su Nombre: ");
			String nombre = lectura.nextLine();
			System.out.println("Ingrese su Apellido: ");
			String apellido = lectura.nextLine();
			System.out.println("Ingrese su Direccion: ");
			String address = lectura.nextLine();
			System.out.println("Ingrese su Ciudad: ");
			ciudad = lectura.nextLine();
			System.out.println("Ingrese su Telefono: ");
			String telefono = lectura.nextLine();
			System.out.println("Nuevo owner: " + nombre + " - " + apellido + " - " + ciudad + " - " + telefono);
			String sqlInsert = "INSERT INTO owners (first_name,last_name,address,city,telephone) VALUES (?,?,?,?,?)";

			PreparedStatement pstmt = conn.prepareStatement(sqlInsert);

			if (pstmt != null) {
				pstmt.setString(1, nombre);
				pstmt.setString(2, apellido);
				pstmt.setString(3, address);
				pstmt.setString(4, ciudad);
				pstmt.setString(5, telefono);
				pstmt.execute();
				pstmt.close();
				System.out.println("Compruebo");
				Tarea1(conn);
			}

		}
		catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	// Taller 3
	public void Tarea3(Connection conn) throws Exception {
		try {

			System.out.println("TALLER 3");

			Scanner lectura = new Scanner(System.in);

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "petclinic", "petclinic");

			System.out.println("Ingrese una nueva Ciudad que reemplace a la anterior: ");
			String nuevaCiudad = lectura.nextLine();

			String sqlUpdate = "UPDATE owners SET city=? WHERE city='Soria'";
			PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);

			if (pstmt != null) {
				pstmt.setString(1, nuevaCiudad);
				System.out.println("Ciudad reemplazada con exito");
				pstmt.execute();
				pstmt.close();
				System.out.println("Compruebo");
				Tarea1(conn);
			}

		}
		catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	// Taller 4
	public void Tarea4(Connection conn) throws Exception {
		try {
			Scanner lectura = new Scanner(System.in);
			System.out.println("TALLER 4");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "petclinic", "petclinic");

			System.out.println("Ingrese Nombre a buscar: ");
			String nombre = lectura.nextLine();
			System.out.println("Ingrese Apellido a buscar: ");
			String apellido = lectura.nextLine();

			String sqlUpdate = "SELECT * FROM owners WHERE first_name=? and last_name=?";
			PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);
			final Object[][] user = new String[4][];
			int i = 0;
			if (pstmt != null) {
				pstmt.setString(1, nombre);
				pstmt.setString(2, apellido);
				System.out.println("Buscando " + nombre + " " + apellido);
				ResultSet res = pstmt.executeQuery();
				String id = "";
				String fn = "";
				String ln = "";
				String add = "";
				String c = "";
				String tlf = "";
				if (res != null) {
					while (res.next()) {
						id = res.getString("id");
						fn = res.getString("first_name");
						ln = res.getString("last_name");
						add = res.getString("address");
						c = res.getString("city");
						tlf = res.getString("telephone");
						user[i] = new String[] { id.toString(), fn, ln, add, c, tlf };
						System.out.format("|%1$-10s| %2$-10s |%3$-15s| %4$-15s |%5$-10s |%6$-15s|\n", user[i]);
					}
				}
				pstmt.close();
			}

		}
		catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}

	// Reto
	public void Reto(Connection conn, String ciudad) throws Exception {
		try {
			System.out.println("RETO");

			Scanner lectura = new Scanner(System.in);

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "petclinic", "petclinic");

			System.out.println("Ingrese su Nombre: ");
			String nombre = lectura.nextLine();
			System.out.println("Ingrese su Apellido: ");
			String apellido = lectura.nextLine();
			System.out.println("Ingrese su Direccion: ");
			String address = lectura.nextLine();
			System.out.println("Ingrese su Ciudad: ");
			ciudad = lectura.nextLine();
			System.out.println("Ingrese su Telefono: ");
			String telefono = lectura.nextLine();

			Integer miId = -1;
			Owner nuevoPropietario = new Owner();

			nuevoPropietario.setFirstName(nombre);
			nuevoPropietario.setLastName(apellido);
			nuevoPropietario.setAddress(address);
			nuevoPropietario.setCity(ciudad);
			nuevoPropietario.setTelephone(telefono);

			String sqlnewPropietario = "INSERT INTO owners (first_name,last_name,address,city,telephone) VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sqlnewPropietario);

			// Inserto nuevo owner
			if (pstmt != null) {
				pstmt.setString(1, nuevoPropietario.getFirstName());
				pstmt.setString(2, nuevoPropietario.getLastName());
				pstmt.setString(3, nuevoPropietario.getAddress());
				pstmt.setString(4, nuevoPropietario.getCity());
				pstmt.setString(5, nuevoPropietario.getTelephone());
				pstmt.execute();
				pstmt.close();
			}

			// Hago SELECT para pillarme el ID del que acabo de meter
			String getIdOwner = "SELECT id FROM owners WHERE first_name=? and last_name=?";
			pstmt = conn.prepareStatement(getIdOwner);
			if (pstmt != null) {
				pstmt.setString(1, nuevoPropietario.getFirstName());
				pstmt.setString(2, nuevoPropietario.getLastName());
				System.out
						.println("Buscando " + nuevoPropietario.getFirstName() + " " + nuevoPropietario.getLastName());
				ResultSet res = pstmt.executeQuery();
				if (res.next()) {
					miId = res.getInt("id");
					System.out.println("MI ID: " + miId);
				}
			}
			// Me inserto como propietario de mascota
			String sqlAddPet = "INSERT INTO pets (name,birth_date,type_id,owner_id) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sqlAddPet);
			long millis = System.currentTimeMillis();
			java.sql.Date birth = new java.sql.Date(millis);

			if (pstmt != null) {
				pstmt.setString(1, "mipet");
				pstmt.setDate(2, birth);
				pstmt.setInt(3, 2);
				pstmt.setInt(4, miId);

				pstmt.execute();
				pstmt.close();
			}
			System.out.println(birth.toString());
			// Compruebo que todo okey

			String getPetOwner = "SELECT * FROM pets WHERE owner_id=?";
			pstmt = conn.prepareStatement(getPetOwner);

			if (pstmt != null) {
				pstmt.setInt(1, miId);
				ResultSet res = pstmt.executeQuery();

				while (res.next()) {
					miId = res.getInt("id");
					System.out.println("MI Pet: " + res.getInt("id") + "," + res.getString("name") + ","
							+ res.getDate("birth_date") + "," + res.getInt("type_id") + "," + res.getInt("owner_id"));
				}
			}
			// Borro pet
			String deletePetOwner = "DELETE FROM pets WHERE owner_id=?";
			String deleteOwner = "DELETE FROM owners WHERE first_name=? and last_name=?";
			pstmt = conn.prepareStatement(deletePetOwner);
			if (pstmt != null) {
				pstmt.setInt(1, miId);
				pstmt.executeUpdate();
				System.out.println("Reseteado pets");
			}

		}
		catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		System.out.println("Run started");
		Connection conn = null;
		Scanner lectura = new Scanner(System.in);
		String ciudad = "";

		////////////////////////////////////////////
		// TALLER 1
		////////////////////////////////////////////

		Tarea1(conn);

		////////////////////////////////////////////
		// TALLER 2
		////////////////////////////////////////////

		Tarea2(conn, ciudad);

		////////////////////////////////////////////
		// TALLER 3
		////////////////////////////////////////////

		Tarea3(conn);

		////////////////////////////////////////////
		// TALLER 4
		////////////////////////////////////////////

		Tarea4(conn);

		////////////////////////////////////////////
		// RETO
		////////////////////////////////////////////

		Reto(conn, ciudad);

	}

}
