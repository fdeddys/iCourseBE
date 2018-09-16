package com.ddabadi.rest;

import com.ddabadi.model.enu.EntityStatus;

import io.swagger.annotations.Api;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/util")
@Api(value = "util", description = "CRUD for entity Utility [needed by system] ")
public class UtilRestController {

//    @GetMapping("globaltypelist")
//    List<GlobalTypeEnum> getAllGlobalType(){
//        return Arrays.asList(GlobalTypeEnum.values());
//    }

    @GetMapping("statuses")
    List<EntityStatus> getAllStatus(){
        return Arrays.asList(EntityStatus.values());
    }

    @GetMapping(value = "appVersion")
    public String index() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader("pom.xml"));
            return model.getVersion();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "NOTFOUND";
    }


}
