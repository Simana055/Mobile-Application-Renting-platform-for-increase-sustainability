package com.example.myapplication_rentme.Database;

public class CartContract {

    public CartContract() {
    }

    //set the name of the table
    public static final String CARTTABLE = "CARTTABLE";

    //set the names of the columes
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ID = "ProductId";
    public static final String COLUMN_CATEGORY_NAME = "CategoryName";
    public static final String COLUMN_PRODUCT_DISC = "ProductDisc";
    public static final String COLUMN_PRODUCT_IMAGE = "ProductImage";
    public static final String COLUMN_PRODUCT_NAME  = "ProductName";
    public static final String COLUMN_OFFER_PRICE = "OfferPrice";
    public static final String COLUMN_ISSPECIAL = "IsSpecialOffer";
    public static final String COLUMN_PRODUCT_PRICE = "ProductPrice";
    public static final String COLUMN_PRODUCT_QUANTITY = "ProductQuantity";

    public static final String SQL_CREATE_ENTRIES_NOTES =
            "CREATE TABLE " + CARTTABLE + " (" +
                    CartContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CartContract.COLUMN_PRODUCT_QUANTITY + " INTEGER, " +
                    CartContract.COLUMN_PRODUCT_ID + " TEXT, " +
                    CartContract.COLUMN_PRODUCT_NAME + " TEXT, " +
                    CartContract.COLUMN_PRODUCT_IMAGE + " TEXT, " +
                    CartContract.COLUMN_PRODUCT_DISC + " TEXT, " +
                    CartContract.COLUMN_PRODUCT_PRICE + " TEXT, " +
                    CartContract.COLUMN_CATEGORY_NAME + " TEXT, " +
                    CartContract.COLUMN_OFFER_PRICE + " TEXT, " +
                    CartContract.COLUMN_ISSPECIAL + " INTEGER)";

}
