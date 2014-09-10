package com.smartx.bill.mepad.mestore.entity;

/** 
* @ClassName: AppDownloading 
* @Description: app downloading status's entity
* @author coney Geng
* @date 2014年9月9日 下午3:34:55 
*  
*/
public class AppDownloading {

	    // private variables
	    public int _id;
	    public int _download_id;
	    public String _app_name;
	    public int _current_size;
	    public int _all_size;

	    public AppDownloading() {
	    }

	    // constructor
	    public AppDownloading(int id, String name, int _current_size,int _all_size) {
		this._id = id;
		this._app_name = name;
		this._current_size = _current_size;
		this._all_size = _all_size;

	    }

	    // constructor
	    public AppDownloading(String name, int _current_size,int _all_size) {
		this._app_name = name;
		this._current_size = _current_size;
		this._all_size = _all_size;
	    }

	    // getting ID
	    public int getID() {
		return this._id;
	    }

	    // setting id
	    public void setID(int id) {
		this._id = id;
	    }

	    // getting name
	    public String getAppName() {
		return this._app_name;
	    }

	    // setting name
	    public void setAppName(String app_name) {
		this._app_name = app_name;
	    }

	    // getting phone number
	    public int getCurrentSize() {
		return this._current_size;
	    }

	    // setting phone number
	    public void setCurrentSize(int current_size) {
		this._current_size = current_size;
	    }

	    // getting all_size
	    public int getAllSize() {
		return this._all_size;
	    }

	    // setting all_size
	    public void setAllSize(int all_size) {
		this._all_size = all_size;
	    }

	}
