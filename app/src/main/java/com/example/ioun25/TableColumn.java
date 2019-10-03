package com.example.ioun25;
/**
 * Created by Jerry on 1/17/2018.
 */

/* This class represent one table column*/
public class TableColumn {

    // This is the table column name.
    private String columnName;

    // This is the table column value.
    private String columnValue;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}