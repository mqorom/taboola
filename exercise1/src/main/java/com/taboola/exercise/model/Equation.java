package com.taboola.exercise.model;

import java.util.ArrayList;
import java.util.List;

public class Equation {

    private String variableName;
    private boolean isVariableEquation;
    private List<Item> items;
    private List<String> postIncrementsVariables;
    private List<String> postDecrementsVariables;

    public Equation(String variableName, boolean isVariableEquation) {
        this.variableName = variableName;
        this.isVariableEquation = isVariableEquation;
        this.items = new ArrayList<>();
        this.postIncrementsVariables = new ArrayList<>();
        this.postDecrementsVariables = new ArrayList<>();
    }

    public void addPostIncrementsVariable(String variable) {
        postIncrementsVariables.add(variable);
    }

    public void addPostDecrementsVariable(String variable) {
        postDecrementsVariables.add(variable);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @return {@link #variableName}
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * @return {@link #isVariableEquation}
     */
    public boolean isVariableEquation() {
        return isVariableEquation;
    }

    /**
     * @return {@link #items}
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @return {@link #postIncrementsVariables}
     */
    public List<String> getPostIncrementsVariables() {
        return postIncrementsVariables;
    }

    /**
     * @return {@link #postDecrementsVariables}
     */
    public List<String> getPostDecrementsVariables() {
        return postDecrementsVariables;
    }
}
