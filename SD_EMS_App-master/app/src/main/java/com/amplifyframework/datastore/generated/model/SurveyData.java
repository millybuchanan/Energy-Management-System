package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the SurveyData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "SurveyData", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class SurveyData implements Model {
  public static final QueryField ID = field("SurveyData", "id");
  public static final QueryField IDEAL_BRIGHTNESS = field("SurveyData", "idealBrightness");
  public static final QueryField IDEAL_TEMP = field("SurveyData", "idealTemp");
  public static final QueryField AGE = field("SurveyData", "age");
  public static final QueryField ZIPCODE = field("SurveyData", "zipcode");
  public static final QueryField USERNAME = field("SurveyData", "username");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Float") Double idealBrightness;
  private final @ModelField(targetType="Float") Double idealTemp;
  private final @ModelField(targetType="Int") Integer age;
  private final @ModelField(targetType="Int") Integer zipcode;
  private final @ModelField(targetType="String") String username;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public Double getIdealBrightness() {
      return idealBrightness;
  }
  
  public Double getIdealTemp() {
      return idealTemp;
  }
  
  public Integer getAge() {
      return age;
  }
  
  public Integer getZipcode() {
      return zipcode;
  }
  
  public String getUsername() {
      return username;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private SurveyData(String id, Double idealBrightness, Double idealTemp, Integer age, Integer zipcode, String username) {
    this.id = id;
    this.idealBrightness = idealBrightness;
    this.idealTemp = idealTemp;
    this.age = age;
    this.zipcode = zipcode;
    this.username = username;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      SurveyData surveyData = (SurveyData) obj;
      return ObjectsCompat.equals(getId(), surveyData.getId()) &&
              ObjectsCompat.equals(getIdealBrightness(), surveyData.getIdealBrightness()) &&
              ObjectsCompat.equals(getIdealTemp(), surveyData.getIdealTemp()) &&
              ObjectsCompat.equals(getAge(), surveyData.getAge()) &&
              ObjectsCompat.equals(getZipcode(), surveyData.getZipcode()) &&
              ObjectsCompat.equals(getUsername(), surveyData.getUsername()) &&
              ObjectsCompat.equals(getCreatedAt(), surveyData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), surveyData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getIdealBrightness())
      .append(getIdealTemp())
      .append(getAge())
      .append(getZipcode())
      .append(getUsername())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("SurveyData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("idealBrightness=" + String.valueOf(getIdealBrightness()) + ", ")
      .append("idealTemp=" + String.valueOf(getIdealTemp()) + ", ")
      .append("age=" + String.valueOf(getAge()) + ", ")
      .append("zipcode=" + String.valueOf(getZipcode()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static SurveyData justId(String id) {
    return new SurveyData(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      idealBrightness,
      idealTemp,
      age,
      zipcode,
      username);
  }
  public interface BuildStep {
    SurveyData build();
    BuildStep id(String id);
    BuildStep idealBrightness(Double idealBrightness);
    BuildStep idealTemp(Double idealTemp);
    BuildStep age(Integer age);
    BuildStep zipcode(Integer zipcode);
    BuildStep username(String username);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Double idealBrightness;
    private Double idealTemp;
    private Integer age;
    private Integer zipcode;
    private String username;
    @Override
     public SurveyData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new SurveyData(
          id,
          idealBrightness,
          idealTemp,
          age,
          zipcode,
          username);
    }
    
    @Override
     public BuildStep idealBrightness(Double idealBrightness) {
        this.idealBrightness = idealBrightness;
        return this;
    }
    
    @Override
     public BuildStep idealTemp(Double idealTemp) {
        this.idealTemp = idealTemp;
        return this;
    }
    
    @Override
     public BuildStep age(Integer age) {
        this.age = age;
        return this;
    }
    
    @Override
     public BuildStep zipcode(Integer zipcode) {
        this.zipcode = zipcode;
        return this;
    }
    
    @Override
     public BuildStep username(String username) {
        this.username = username;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Double idealBrightness, Double idealTemp, Integer age, Integer zipcode, String username) {
      super.id(id);
      super.idealBrightness(idealBrightness)
        .idealTemp(idealTemp)
        .age(age)
        .zipcode(zipcode)
        .username(username);
    }
    
    @Override
     public CopyOfBuilder idealBrightness(Double idealBrightness) {
      return (CopyOfBuilder) super.idealBrightness(idealBrightness);
    }
    
    @Override
     public CopyOfBuilder idealTemp(Double idealTemp) {
      return (CopyOfBuilder) super.idealTemp(idealTemp);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
    
    @Override
     public CopyOfBuilder zipcode(Integer zipcode) {
      return (CopyOfBuilder) super.zipcode(zipcode);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
  }
  
}
