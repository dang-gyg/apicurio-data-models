/*
 * Copyright 2019 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.datamodels.core.validation.rules.invalid.value;

import java.util.ArrayList;
import java.util.List;

import io.apicurio.datamodels.core.Constants;
import io.apicurio.datamodels.core.models.common.Parameter;
import io.apicurio.datamodels.core.util.VisitorUtil;
import io.apicurio.datamodels.core.validation.ValidationRuleMetaData;
import io.apicurio.datamodels.core.visitors.ConsumesProducesFinder;
import io.apicurio.datamodels.core.visitors.TraverserDirection;
import io.apicurio.datamodels.openapi.models.OasParameter;

/**
 * Implements the Form Data Parameter Not Allowed rule.
 * @author eric.wittmann@gmail.com
 */
public class OasFormDataParamNotAllowedRule extends OasInvalidPropertyValueRule {

    /**
     * Constructor.
     * @param ruleInfo
     */
    public OasFormDataParamNotAllowedRule(ValidationRuleMetaData ruleInfo) {
        super(ruleInfo);
    }

    /**
     * @see io.apicurio.datamodels.combined.visitors.CombinedAllNodeVisitor#visitParameter(io.apicurio.datamodels.core.models.common.Parameter)
     */
    @Override
    public void visitParameter(Parameter node) {
        OasParameter param = (OasParameter) node;
        if (equals(param.in, "formData")) {
            ConsumesProducesFinder finder = new ConsumesProducesFinder();
            VisitorUtil.visitTree(node, finder, TraverserDirection.up);
            
            List<String> consumes = finder.consumes;
            if (!hasValue(consumes)) {
                consumes = new ArrayList<>();
            }
            boolean valid = consumes.indexOf("application/x-www-form-urlencoded") >= 0 || consumes.indexOf("multipart/form-data") >= 0;
            this.reportIfInvalid(valid, node, Constants.PROP_CONSUMES, map());
        }
    }

}