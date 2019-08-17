/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }
  
  
  @RequestMapping(value = "/paragens", method = RequestMethod.GET)
  @ResponseBody
  String listParagens() {
      ArrayList<String> paragens = new ArrayList<>();
      paragens.add("Oriente");
      paragens.add("Sete Rios");
      paragens.add("Entrecampos");
      paragens.add("Sintra");
      
      Gson gson = new Gson();
      return gson.toJson(paragens);
  }
  
  @RequestMapping(value = "/bff", method = RequestMethod.GET)
  @ResponseBody
  String bff() {
      String bff = "João Viado Machado";
      
      Gson gson = new Gson();
      return gson.toJson(bff);
  }
  
  @RequestMapping(value = "/professores", method = RequestMethod.GET)
  @ResponseBody
  String listProfessores() {
      ArrayList<String> cadeirasP1 = new ArrayList<>();
      ArrayList<String> cadeirasP2 = new ArrayList<>();
      
      cadeirasP1.add("AED");
      cadeirasP1.add("LP2");
      cadeirasP1.add("TFC");
      Professor p1 = new Professor("Bruno Cipriano", 27, "ULHT", cadeirasP1);
      
      cadeirasP2.add("FP");
      cadeirasP2.add("LP1");
      cadeirasP2.add("Computação Móvel");
      cadeirasP2.add("TFC");
      Professor p2 = new Professor("Pedro Alves", 30, "ULHT", cadeirasP2);
      
      
      ArrayList<Professor> profs = new ArrayList<>();
      profs.add(p1);
      profs.add(p2);
      
      Gson gson = new Gson();
      return gson.toJson(profs);
  }
  
  @RequestMapping(value = "/distritos/list", method = RequestMethod.GET)
  @ResponseBody
  String listDistritos() {
      DataBaseConnector jdbc = DataBaseConnector.getInstance();
      
      ArrayList<Distrito> response = jdbc.listDistritos();
      Gson gson = new Gson();
      return gson.toJson(response);
  }
  
  @RequestMapping(value = "/distritos/{id}", method = RequestMethod.GET)
  @ResponseBody
  String getDistritoById(@PathVariable(value = "id") Integer distritoId) {
      DataBaseConnector jdbc = DataBaseConnector.getInstance();
      
      Distrito response = jdbc.getDistritoById(distritoId);
      Gson gson = new Gson();
      return gson.toJson(response);
  }
  
  @RequestMapping("/hello")
  String hello(Map<String, Object> model) {
      RelativisticModel.select();
      String energy = System.getenv().get("ENERGY");
      
      if(energy == null) {
          energy = "12 GeV";
      }
      
      Amount<Mass> m = Amount.valueOf(energy).to(KILOGRAM);
      model.put("science", "E=mc^2: " + energy + " = " + m.toString());
      return "hello";
  }

  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }

}
