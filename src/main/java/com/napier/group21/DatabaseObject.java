package com.napier.group21;

/**
 * Share by all different report object types that can retrieved from the database.
 * Allows ArrayList of DatabaseObject to use any record which implements DatabaseObject.
 */
public interface DatabaseObject {
    @Override
    String toString();

    /**
     * @return Formatted columns belonging to this report type
     */
    String getColumnString();
}

