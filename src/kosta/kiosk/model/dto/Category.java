package kosta.kiosk.model.dto;

public class Category{

	    private int categoryId;       // 카테고리 번호
	    private String categoryName;  // 카테고리 이름

	    public Category() {
	    }

	    // 카테고리 추가용: DB에서 ID를 자동 생성하는 경우
	    public Category(int categoryId, String categoryName) {
	        this.categoryId = categoryId;
	    	this.categoryName = categoryName;
	  
	    }

	    public int getCategoryId() {
	        return categoryId;
	    }

	    public void setCategoryId(int categoryId) {
	        this.categoryId = categoryId;
	    }

	    public String getCategoryName() {
	        return categoryName;
	    }

	    public void setCategoryName(String categoryName) {
	        this.categoryName = categoryName;
	    }

	    @Override
	    public String toString() {
	        return "CategoryDTO [categoryId=" + categoryId 
	        		+ ", categoryName = " + categoryName + "]";
	    }
	}
