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
  public static final QueryField LIGHT = field("SurveyData", "light");
  public static final QueryField TEMPERATURE = field("SurveyData", "temperature");
  public static final QueryField PWM = field("SurveyData", "pwm");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int") Integer light;
  private final @ModelField(targetType="Int") Integer temperature;
  private final @ModelField(targetType="Int") Integer pwm;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public Integer getLight() {
      return light;
  }
  
  public Integer getTemperature() {
      return temperature;
  }
  
  public Integer getPwm() {
      return pwm;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private SurveyData(String id, Integer light, Integer temperature, Integer pwm) {
    this.id = id;
    this.light = light;
    this.temperature = temperature;
    this.pwm = pwm;
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
              ObjectsCompat.equals(getLight(), surveyData.getLight()) &&
              ObjectsCompat.equals(getTemperature(), surveyData.getTemperature()) &&
              ObjectsCompat.equals(getPwm(), surveyData.getPwm()) &&
              ObjectsCompat.equals(getCreatedAt(), surveyData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), surveyData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getLight())
      .append(getTemperature())
      .append(getPwm())
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
      .append("light=" + String.valueOf(getLight()) + ", ")
      .append("temperature=" + String.valueOf(getTemperature()) + ", ")
      .append("pwm=" + String.valueOf(getPwm()) + ", ")
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      light,
      temperature,
      pwm);
  }
  public interface BuildStep {
    SurveyData build();
    BuildStep id(String id);
    BuildStep light(Integer light);
    BuildStep temperature(Integer temperature);
    BuildStep pwm(Integer pwm);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Integer light;
    private Integer temperature;
    private Integer pwm;
    @Override
     public SurveyData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new SurveyData(
          id,
          light,
          temperature,
          pwm);
    }
    
    @Override
     public BuildStep light(Integer light) {
        this.light = light;
        return this;
    }
    
    @Override
     public BuildStep temperature(Integer temperature) {
        this.temperature = temperature;
        return this;
    }
    
    @Override
     public BuildStep pwm(Integer pwm) {
        this.pwm = pwm;
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
    private CopyOfBuilder(String id, Integer light, Integer temperature, Integer pwm) {
      super.id(id);
      super.light(light)
        .temperature(temperature)
        .pwm(pwm);
    }
    
    @Override
     public CopyOfBuilder light(Integer light) {
      return (CopyOfBuilder) super.light(light);
    }
    
    @Override
     public CopyOfBuilder temperature(Integer temperature) {
      return (CopyOfBuilder) super.temperature(temperature);
    }
    
    @Override
     public CopyOfBuilder pwm(Integer pwm) {
      return (CopyOfBuilder) super.pwm(pwm);
    }
  }
  
}
