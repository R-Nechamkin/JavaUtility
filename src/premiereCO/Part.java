package premiereCO;

import java.math.BigDecimal;

public class Part extends DBTable{
	String partNum;
	String description;
	int unitsOnHand;
	String category;
	int warehouse;
	BigDecimal price;
	
	
	
	
	public Part(String partNum, String description, int unitsOnHand, String category, int warehouse, BigDecimal price) {
		super();
		this.partNum = partNum;
		this.description = description;
		this.unitsOnHand = unitsOnHand;
		this.category = category;
		this.warehouse = warehouse;
		this.price = price;
	}




	public String createInsertStatement() {
		return String.format("INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) "
				+ "VALUES ('%s', '%s', %d, '%s', %d, %s)", partNum, description, unitsOnHand, category, warehouse, price.toString());
	}
}
