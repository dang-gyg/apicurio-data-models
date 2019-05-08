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

package io.apicurio.datamodels.core.factories;

import io.apicurio.datamodels.asyncapi.io.AaiDataModelReader;
import io.apicurio.datamodels.asyncapi.io.AaiDataModelWriter;
import io.apicurio.datamodels.core.io.DataModelReader;
import io.apicurio.datamodels.core.io.DataModelWriter;
import io.apicurio.datamodels.core.models.Document;
import io.apicurio.datamodels.core.models.DocumentType;
import io.apicurio.datamodels.core.validation.ValidationProblemsResetVisitor;
import io.apicurio.datamodels.core.validation.ValidationVisitor;
import io.apicurio.datamodels.openapi.v2.io.Oas20DataModelReader;
import io.apicurio.datamodels.openapi.v2.io.Oas20DataModelWriter;
import io.apicurio.datamodels.openapi.v3.io.Oas30DataModelReader;
import io.apicurio.datamodels.openapi.v3.io.Oas30DataModelWriter;

/**
 * @author eric.wittmann@gmail.com
 */
public class VisitorFactory {
    
    public static final ValidationProblemsResetVisitor createValidationProblemsResetVisitor(Document doc) {
        return new ValidationProblemsResetVisitor();
    }
    
    public static final ValidationVisitor createValidationVisitor(Document doc) {
        return new ValidationVisitor(doc);
    }
    
    public static final DataModelReader<?> createDataModelReader(DocumentType type) {
        switch (type) {
            case asyncapi2:
                return new AaiDataModelReader();
            case openapi2:
                return new Oas20DataModelReader();
            case openapi3:
                return new Oas30DataModelReader();
            default:
                throw new RuntimeException("Failed to create a data model reader for type: " + type);
        }
    }

    public static DataModelWriter createDataModelWriter(Document doc) {
        switch (doc.getDocumentType()) {
            case asyncapi2:
                return new AaiDataModelWriter();
            case openapi2:
                return new Oas20DataModelWriter();
            case openapi3:
                return new Oas30DataModelWriter();
            default:
                throw new RuntimeException("Failed to create a validation visitor for type: " + doc.getDocumentType());
        }
    }

}