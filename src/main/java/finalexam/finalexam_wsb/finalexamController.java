/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam.finalexam_wsb;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Muhammad Irsyad Rizky Ananda
 * 20200140070
 */
@RestController
@CrossOrigin

public class finalexamController {
    Orang data = new Orang();
    OrangJpaController ctrl =  new OrangJpaController();
    
    @RequestMapping(value="/GET/{id}")
    public String getNameById(@PathVariable("id") int id){
        try{
            data=ctrl.findOrang(id);
            
        }catch(Exception e){
            
        }return data.getNama();
    }
    @GetMapping("/GET")
    public List<Orang> getAll(){
        List<Orang> dummy = new ArrayList<>();
        try {
            dummy=ctrl.findOrangEntities();
        } catch (Exception e) {
        }return dummy;
    }
//    @RequestMapping(value="/GET",method=RequestMethod.GET,consumes=APPLICATION_JSON_VALUE)
//    public List<Orang>getAll(){
//        List<Orang> dummy = new ArrayList();
//        try{
//            dummy=ctrl.findOrangEntities();
//        }catch(Exception e){
//            dummy=List.of();
//        }
//        return dummy;
//    }
    @RequestMapping(value="POST", method=RequestMethod.POST,consumes=APPLICATION_JSON_VALUE)
    public String createData(HttpEntity<String> paket){
        String message="";
        try{
          String json_receive = paket.getBody();
            ObjectMapper map = new ObjectMapper();
            Orang data = new Orang();
            data= map.readValue(json_receive, Orang.class);
            ctrl.create(data);
            message=data.getNama() + " Disimpan";
        } catch (Exception e) {
            message="Data gagal disimpan";
        }
        return message;
    }
     @RequestMapping(value="/PUT",method=RequestMethod.PUT,consumes=APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> kiriman){
        String message="no action";
        try {
            String json_receive=kiriman.getBody();
            ObjectMapper mapper = new ObjectMapper();
            Orang newdata= new Orang();
            newdata=mapper.readValue(json_receive, Orang.class);
            ctrl.edit(newdata);
            message=newdata.getNama()+" berhasil diupdate";
        } catch (Exception e) {
        }
        return message;
    }
    @RequestMapping(value="/DELETE",method=RequestMethod.DELETE,consumes=APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> kiriman){
        String message="no action";
        try {
            String json_receive=kiriman.getBody();
            ObjectMapper mapper = new ObjectMapper();
            Orang newdata= new Orang();
            newdata=mapper.readValue(json_receive, Orang.class);
            ctrl.destroy(newdata.getId());
            message=newdata.getNama()+" berhasil dihapus";
        } catch (Exception e) {
        }
        return message;
    }
 
}
