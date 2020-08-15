package io.holunda.decision.model.data

import io.holunda.decision.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

internal class DataTypeTest {

  @Test
  fun `correct values`() {
    assertThat(StringDataType.type).isEqualTo(String::class.java)
    assertThat(StringDataType.name).isEqualTo("STRING")
    assertThat(StringDataType.typeRef).isEqualTo("string")

    assertThat(BooleanDataType.type).isEqualTo(Boolean::class.java)
    assertThat(BooleanDataType.name).isEqualTo("BOOLEAN")
    assertThat(BooleanDataType.typeRef).isEqualTo("boolean")

    assertThat(IntegerDataType.type).isEqualTo(Int::class.java)
    assertThat(IntegerDataType.name).isEqualTo("INTEGER")
    assertThat(IntegerDataType.typeRef).isEqualTo("integer")

    assertThat(LongDataType.type).isEqualTo(Long::class.java)
    assertThat(LongDataType.name).isEqualTo("LONG")
    assertThat(LongDataType.typeRef).isEqualTo("long")

    assertThat(DoubleDataType.type).isEqualTo(Double::class.java)
    assertThat(DoubleDataType.name).isEqualTo("DOUBLE")
    assertThat(DoubleDataType.typeRef).isEqualTo("double")

    assertThat(DateDataType.type).isEqualTo(Date::class.java)
    assertThat(DateDataType.name).isEqualTo("DATE")
    assertThat(DateDataType.typeRef).isEqualTo("date")
  }

  @Test
  fun `look up by typeref`() {
    assertThat(DataType.valueByTypeRef(BooleanDataType.typeRef)).isEqualTo(BooleanDataType)
    assertThat(DataType.valueByTypeRef(IntegerDataType.typeRef)).isEqualTo(IntegerDataType)
    assertThat(DataType.valueByTypeRef(LongDataType.typeRef)).isEqualTo(LongDataType)
    assertThat(DataType.valueByTypeRef(DoubleDataType.typeRef)).isEqualTo(DoubleDataType)
    assertThat(DataType.valueByTypeRef(DateDataType.typeRef)).isEqualTo(DateDataType)
    assertThat(DataType.valueByTypeRef(StringDataType.typeRef)).isEqualTo(StringDataType)

  }
}
