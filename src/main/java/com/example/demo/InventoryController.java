package com.example.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.PartType;



@RestController
@RequestMapping("/inventory")
public class InventoryController {
	
	
	@Autowired
	private InventoryService inventoryService;
	
	
	public InventoryController() {
		inventoryService=new InventoryService();
	}
	
	@PostMapping(path="part_types")
	public PartType addPartType(@RequestBody PartType partType) {
		
	
		return inventoryService.addPartType(partType);
	}
	
	@GetMapping(path="part_types")
	public List<PartType> getPartTypes(){
		return inventoryService.getAllPartTypes();
	}
	
	
	@GetMapping(path="part_types/{partTypeID}")
	public PartType getPartType(@PathVariable("partTypeID")int partTypeID){
		return inventoryService.getPartTypeById(partTypeID);
	}
	
	@GetMapping(path="part_types/supplier/{supplierID}")
	public List<PartType> searchPartTypeBySupplierID(@PathVariable("supplierID") int supplierID) {
	    return inventoryService.searchPartTypeBySupplierID(supplierID);
	}
	
	@PutMapping(path="part_types/{partTypeID}")
	public String updatePartType(@PathVariable("partTypeID")int partTypeID, @RequestBody PartType partType) {
		
		partType.setId(partTypeID);
		PartType updatedPartType=inventoryService.updatePartType(partType);
		if (updatedPartType==null)
			return "part type doesn't exist";
		else return updatedPartType.toString();
	}
	
	@DeleteMapping(path="part_types/{partTypeID}")
	public void deletePartType(@PathVariable("partTypeID")int partTypeID) {
		inventoryService.deletePartType(partTypeID);
		
	}
	@GetMapping(path="spare_parts")
	public List<SparePart> getSpareParts() {
	    return inventoryService.getAllSpareParts();
	}
	
	@GetMapping(path="spare_parts/{serialNum}")
	public SparePart getSparePartBySerialNumber(@PathVariable("serialNum")String serialNum) {
		return inventoryService.getSparePartBySerialNumber(serialNum);
	}
	@PostMapping(path="spare_parts")
	public String addSparePart(@RequestBody SparePart sparePart) {
		
		SparePart addedSparePart=inventoryService.addSparePart(sparePart);
		if (addedSparePart==null)
			return "Spare Part already exists";
		else return addedSparePart.toString();
		
	}
	
	@GetMapping(path="spare_parts/part_type/{partTypeID}")
	public List<SparePart> searchSparePartByPartTypeID(@PathVariable("partTypeID")int partTypeID) {
	    return inventoryService.searchSparePartByPartTypeID(partTypeID);
	}
	
	
	@PutMapping(path="spare_parts/{serialNum}")
	public String updateSparePart(@PathVariable("serialNum") String serialNum, @RequestBody SparePart sparePart) {
		
		if (!sparePart.getSerialNumber().equals(serialNum))
			return "can't change serial number";
		
		SparePart updatedSparePart=inventoryService.updateSparePart(sparePart);
		if (updatedSparePart==null)
			return "spare part doesn't exist";
		else return updatedSparePart.toString();
			
	}
	@DeleteMapping(path="spare_parts/{serialNum}")
	public void deleteSparePart(@PathVariable("serialNum")String serialNum) {
		inventoryService.deleteSparePart(serialNum);
			
	}
	@PostMapping(path="spare_parts_reservation")
	public int reserveSpareParts(@RequestBody Map<String, Object> request) throws IOException, URISyntaxException {
	    int partTypeID = (int) request.get("partTypeID");
	    String machineSerialNum = (String) request.get("machineSerialNum");
	    int quantityNeeded = (int) request.get("quantityNeeded");
	    return inventoryService.reserveSpareParts(partTypeID, machineSerialNum, quantityNeeded);
	}

	
	
	
}
