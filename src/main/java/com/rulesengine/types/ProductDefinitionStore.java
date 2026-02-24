package com.rulesengine.types;

import com.rulesengine.products.ProductDefinition;

public interface ProductDefinitionStore {

    ProductDefinition findProductDefinition(Transaction txn);
}
