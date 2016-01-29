package br.com.augustoccesar.model.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will hold the data used for filtering data from objects.
 *
 * Created by augustoccesar on 1/29/16.
 */
public class FilterList {
    private class Filter{
        public String instanceName;
        public String fieldOfInstanceName;
    }

    public static final String BASE_INSTANCE = "BASE_INSTANCE_&";
    private List<Filter> filters;

    public FilterList() {
        this.filters = new ArrayList<Filter>();
    }

    public void addFilter(String instanceName, String fieldOfInstanceName){
        Filter filter = new Filter();
        filter.instanceName = instanceName;
        filter.fieldOfInstanceName = fieldOfInstanceName;
        this.filters.add(filter);
    }

    public boolean checkIfFieldIsInList(String instanceName, String fieldName){
        for(Filter filter : this.filters){
            if(filter.instanceName.equals(instanceName) && filter.fieldOfInstanceName.equals(fieldName)){
                return true;
            }
        }
        return false;
    }

    public int filtersCount(){
        return this.filters.size();
    }


}
