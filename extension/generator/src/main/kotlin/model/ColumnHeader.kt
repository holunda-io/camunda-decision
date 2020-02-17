package io.holunda.decision.generator.model

data class ColumnHeader(
 val key: String,
 val label: String,
 val expressionType: ExpressionType
) {
  companion object {
    @JvmStatic
    @JvmOverloads
    fun stringColumn(key: String, label: String = key) = ColumnHeader(key,label, ExpressionType.STRING)

    @JvmStatic
    @JvmOverloads
    fun booleanColumn(key: String, label: String = key) = ColumnHeader(key,label, ExpressionType.BOOLEAN)

    @JvmStatic
    @JvmOverloads
    fun integerColumn(key: String, label: String = key) = ColumnHeader(key,label, ExpressionType.INTEGER)

    @JvmStatic
    @JvmOverloads
    fun longColumn(key: String, label: String = key) = ColumnHeader(key,label, ExpressionType.LONG)

    @JvmStatic
    @JvmOverloads
    fun doubleColumn(key: String, label: String = key) = ColumnHeader(key,label, ExpressionType.DOUBLE)

    @JvmStatic
    @JvmOverloads
    fun dateColumn(key: String, label: String = key) = ColumnHeader(key,label, ExpressionType.DATE)
  }
}
