package LineIntersectionREST;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.mapbox.services.commons.geojson.Geometry;

import com.mapbox.services.commons.models.Position;

@Service
public class UPService {
	
	JsonEntity jentity;
	
	@PersistenceContext
	EntityManager me;

	@Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
	
	JPosition jpfeature;
	ArrayList<JPosition> jplist = new ArrayList<JPosition>();
	
	@Transactional
    public void uploadFile(MultipartFile file) {
		
		this.check(file);
 	 
        try {      	
           /* Если нужно сохранить файл на диск
            * Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            */
            InputStream is = file.getInputStream();            
            Reader reader = new InputStreamReader(is);
            
            Object obj= JSONValue.parse(reader); 
    		JSONObject jsonObject = (JSONObject) obj;  
    		String geo = jsonObject.toJSONString();
       
            FeatureCollection fc  = FeatureCollection.fromJson(geo);
       
            
            	String dd = fc.toJson();
            		
            	List<Feature> lf = fc.getFeatures();
            	Iterator<Feature> it = lf.iterator();
            	int i=0;
            	while(it.hasNext()) {
            		Feature ee = (Feature) it.next();
            		JsonObject jo = ee.getProperties();           		
            		
            		Geometry go = ee.getGeometry();
            		
            		
            		
            		ArrayList<Position> point = (ArrayList<Position>) go.getCoordinates();
            		System.out.println("Feature: " + i);
            		i++;
            		for(Position p: point) {
            		
            			double longtitude = p.getLongitude();
            			double latitude = p.getLatitude();
            			System.out.println("latitude: " + latitude);
            			System.out.println("longtitude " + longtitude);
            			jpfeature = new JPosition(longtitude,latitude);
            			System.out.println("latitude from ArrayList: " + jpfeature.getLatit());
            			jplist.add(jpfeature);   
            			
            		} 
            		
            		
            	} 
            	this.smh();
            	
            	jentity = new JsonEntity();
            	jentity.setJsonData(dd);
            	me.persist(jentity);
            	System.out.println("SIZE OF JP " + jplist.size());
       	          	   
        	} catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
               + ". Please try again!");
        }
    }
	

	
	
	public void smh() {
		
		
		Iterator itrr = jplist.iterator();
		while(itrr.hasNext()) {
			itrr.next();
			for(int j=i; j<=i;j++) {
				
				this.calculateIntersectionPoint(jplist.get(i).getLatit(), jplist.get(i).getLongt(), jplist.get(j).getLatit(), jplist.get(j).getLongt());
			
	}
	}
	}
	
	
	
	public Optional<Point> calculateIntersectionPoint(double m1, double b1, double m2, double b2) {
		
		    if (m1 == m2) {
		        return Optional.empty();
		    }
		 
		    double x = (b2 - b1) / (m1 - m2);
		    double y = m1 * x + b1;
		 
		    Point point = new Point(x,y);
		   // point.setLocation(x, y);
		    return Optional.of(point);
		}
	
	boolean check(MultipartFile file) {
		
		String ext = "geojson";
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    	if(!extension.equalsIgnoreCase(ext)) {
    		System.out.println("Error - wrong format");
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
               + ". Please try again!");
    	} 
		return true;
		
	}
	
}

	

    

