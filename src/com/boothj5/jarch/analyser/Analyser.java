/* 
 * Analyser.java
 *
 * Copyright (C) 2012 James Booth <boothj5@gmail.com>
 * 
 * This file is part of JArch.
 *
 * JArch is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JArch is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JArch.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.boothj5.jarch.analyser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.boothj5.jarch.configuration.RuleSet;

public class Analyser {

    private final String srcPath;
    private final List<RuleSet> ruleSets;
    private final List<String> errorStrings;
    private int numModuleErrors;
    private int numLayerErrors;

    public Analyser(String srcPath, List<RuleSet> ruleSets) {
        this.srcPath = srcPath;
        this.ruleSets = ruleSets;
        this.errorStrings = new ArrayList<String>();
    }
    
    public void analyse() throws IOException {
        for (RuleSet ruleSet : ruleSets) {
            RuleSetAnalyser analyser = new RuleSetAnalyser(srcPath, 
                    ruleSet.getBasePackage(), 
                    ruleSet.getModules(), 
                    ruleSet.getLayerSpecs());
            
            analyser.analyse();
            
            errorStrings.addAll(analyser.getErrorStrings());
            numModuleErrors += analyser.getNumModuleErrors();
            numLayerErrors += analyser.getNumLayerErrors();
        }
    }
    
    public List<String> getErrorStrings() {
        return errorStrings;
    }
    
    public int getNumModuleErrors() {
        return numModuleErrors;
    }
    
    public int getNumLayerErrors() {
        return numLayerErrors;
    }
}
