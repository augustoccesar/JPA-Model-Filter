package br.com.augustoccesar.model.filter;

import org.apache.commons.lang3.reflect.FieldUtils;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by augustoccesar on 1/29/16.
 */
public class FilterBuilder {
    /**
     * Types of fields according to relationships.
     */
    public static final int NOT_RELATIONSHIP = 0;
    public static final int RELATION_SINGLE = 1;
    public static final int RELATION_MULTIPLE = 2;

    private Object object; // Base object for filtering.
    private HashMap<String, Object> base; // Base HashMap that will be returned.
    private FilterList filterList; // List of filters that will be applied.

    /**
     * Constructor that receives the base object and initialize other data.
     * @param object Base Object
     */
    public FilterBuilder(Object object) {
        this.object = object;
        this.base = new HashMap<String, Object>();
        this.filterList = new FilterList();
    }

    /**
     * Method that build the filter data.
     *
     * @param selectedFieldsParam List of fields that will be filtered.
     * @return This class for continuing the build.
     */
    public FilterBuilder select(String... selectedFieldsParam){
        for(String selectField : selectedFieldsParam){ // For each field in select...
            /**
             * This StringTokenizer will tokenizer the filters according to the following logic:
             *    > if you have a class Father that contains field child (instance of Child), than you
             *      pass through 'select' the string "child.id", that will filter the 'id' field inside
             *      the 'child'.
             */
            StringTokenizer tokenizer = new StringTokenizer(selectField, ".");
            if(tokenizer.countTokens() == 1){ // If there is only one token, than the filter is related to the base Class
                while(tokenizer.hasMoreTokens()){
                    this.filterList.addFilter(FilterList.BASE_INSTANCE, tokenizer.nextToken()); // Add the field filter to the base instance.
                }
            }else if(tokenizer.countTokens() == 2){ // If there is more than one token, than the filter is related to one child of the parent.
                int count = 0;
                String instanceName = null; // Name of the instance inside the parent.
                String instanceFieldName = null; // Name of the field of that child.
                while(tokenizer.hasMoreTokens()){
                    if(count == 0){ // If is the first, than is the instance name.
                        instanceName = tokenizer.nextToken();
                    }else{ // Else is the field name of the instance.
                        instanceFieldName = tokenizer.nextToken();
                    }
                    count++;
                }
                this.filterList.addFilter(FilterList.BASE_INSTANCE, instanceName); // Add the child as a filter for the base instance.
                this.filterList.addFilter(instanceName, instanceFieldName); // Add the field of the child as filter the him.
            }
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public HashMap build(){
        this.base = objectToHashMap(FilterList.BASE_INSTANCE, this.object);
        return this.base;
    }

    // Helpers

    /**
     * Method for checking what kind of relation is the selected field.
     *
     * @param field Field for checking.
     * @return Int that references the kind of relation.
     */
    private int checkIfFieldIsRelation(Field field){
        List<Annotation> fieldAnnotations = Arrays.asList(field.getAnnotations()); // Get the list of Annotations of the Field
        for(Annotation annotation : fieldAnnotations){ // For each annotation...
            if(annotation instanceof OneToOne || annotation instanceof ManyToOne){ // Case is toOne relation...
                return RELATION_SINGLE;
            }else if(annotation instanceof OneToMany || annotation instanceof ManyToMany){ // Case is toMany relation...
                return RELATION_MULTIPLE;
            }
        }
        return NOT_RELATIONSHIP; // Is not a relationship.
    }

    /**
     * Method that map the field inside the base HashMap.
     *
     * @param field Field that will be inserted;
     * @param map Base map.
     * @param value Value that will be inserted inside the base HashMap
     */
    @SuppressWarnings("unchecked")
    private void mapValueInMapByFieldType(Field field, HashMap map, Object value){
        switch (checkIfFieldIsRelation(field)){
            case RELATION_SINGLE:
                map.put(field.getName(), objectToHashMap(field.getName(), value));
                break;
            case RELATION_MULTIPLE:
                List<Object> objects = (List) value;
                List<HashMap> filteredObjects = new ArrayList<HashMap>();
                for(Object o : objects){ // For each object inside the list of objects..
                    HashMap<String, Object> filteredObject = objectToHashMap(field.getName(), o); // Filter a singular object
                    filteredObjects.add(filteredObject); // and add it to the list that will be inside the base map.
                }
                map.put(field.getName(), filteredObjects); // Include in the base map the filtered list.
                break;
            case NOT_RELATIONSHIP:
                map.put(field.getName(), value);
                break;
        }
    }

    /**
     * Method for transforming object into HashMap.
     *
     * @param instanceName Name of the instance inside base Object, case is base Object, than use Constant inside FilterList BASE_INSTANCE.
     * @param object Object that will be transformed.
     * @return HashMap of the object.
     */
    private HashMap objectToHashMap(String instanceName, Object object){
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
        for(Field field : fields) { // Pretty straightforward
            try {
                Object value = FieldUtils.readField(field, object, true);
                if(filterList.filtersCount() > 0) {
                    if (filterList.checkIfFieldIsInList(instanceName, field.getName())) {
                        mapValueInMapByFieldType(field, map, value);
                    }
                }else{
                    mapValueInMapByFieldType(field, map, value);
                }
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}
