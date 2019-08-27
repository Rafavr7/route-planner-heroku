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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import com.example.model.Distrito;
import com.example.database.DataBaseConnector;
import com.example.model.LinhaTransporte;
import com.example.service.LinhasTransporteDAO;
import com.example.service.ParagemDAO;

@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
    
    /**
    Paragem p = ParagemDAO.getParagemByAlias("porto");
    System.out.println(p + "\n");
      
    Paragem p2 = ParagemDAO.getParagemByNome("oriente");
    System.out.println(p2);
    **/
    
    /**
    HashSet<LinhaTransporte> linhas = new HashSet<>();
    LinhasTransporteDAO.getAllLinhasByDistritoInHashSet(3, linhas);
    LinhasTransporteDAO.getAllLinhasByDistritoInHashSet(1, linhas);
    System.out.println(linhas);
    **/
  }
  
  
  @RequestMapping(value = "/distritos/list", method = RequestMethod.GET)
  @ResponseBody
  String listDistritos() {
      DataBaseConnector jdbc = DataBaseConnector.getInstance();
      ArrayList<Distrito> response = null;
      
      try {
          response = jdbc.listDistritos();
      }catch(SQLException ex) {
          System.err.println(ex);
      }
      
      Gson gson = new Gson();
      return gson.toJson(response);
  }
  
  @RequestMapping(value = "/distritos/{id}", method = RequestMethod.GET)
  @ResponseBody
  String getDistritoById(@PathVariable(value = "id") Integer distritoId) {
      DataBaseConnector jdbc = DataBaseConnector.getInstance();
      Distrito response = null;
      
      try {
          response = jdbc.getDistritoById(distritoId);
      }catch(SQLException ex) {
          System.err.println(ex);
      }
      
      Gson gson = new Gson();
      return gson.toJson(response);
  }
  
  @RequestMapping(value = "/paragens/regex={regex}", method = RequestMethod.GET)
  @ResponseBody
  String getNomesParagensLike(@PathVariable(value = "regex") String regex) {
      ArrayList<String> response = null;
      
      try {
          response = ParagemDAO.getNomesParagensLike(regex);
      }catch(SQLException ex) {
          System.err.println(ex);
      }
      
      Gson gson = new Gson();
      return gson.toJson(response);
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
