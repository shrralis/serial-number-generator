# Serial Number Generator
Serial Number Generator is a Kotlin library that provides an API and implementations for generating serial numbers based on flexibly configurable configurations.

## Installation
To use Serial Number Generator, add the following dependency to your project:
### Gradle
```groovy
implementation 'com.shrralis:serial-number-generator:1.0.1'
```
### Maven
```xml
<dependency>
  <groupId>com.shrralis</groupId>
  <artifactId>serial-number-generator</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Usage
### General
To use Serial Number Generator, create an instance of `SerialNumberService`
and pass it the `SerialNumberFormatConfig`, `DecimalValueEncoder`, and `DecimalValueDecoder`
objects that you want to use. Then, you can call the `generate()` method to generate a serial number
based on a given decimal value,
or the `parse()` method to parse a serial number string into its corresponding decimal value.

```kotlin
// create a SerialNumberService instance with a format config, encoders, and decoders
val service = SerialNumberService(formatConfig, decimalValueEncoders, decimalValueDecoders)

// generate a serial number based on a decimal value
val decimalValue = 12345L
val serialNumber = service.generate(decimalValue)   // AM345

// parse a serial number string into its corresponding decimal value
val serialNumberString = "AM345"
val parsedDecimalValue = service.parse(serialNumberString)  // 12345L
```

### Format
To configure the format of generated serial numbers, create an instance of `SerialNumberFormatConfig` with
desired list of `SerialNumberPartConfig` in order they should be present in serial numbers.

Currently, there are 3 default implementations:
 - `StaticSerialNumberPartConfig` - static part of serial number.
 - `Base10SerialNumberPartConfig` - generated base-10 part of serial number.
 - `Base26SerialNumberPartConfig` - generated base-26 part of serial number.

There's also a `SerialNumberFormatConfigConverter` for building `SerialNumberFormatConfig` from String template.
```kotlin
val rawFormatPartPattern = "[A0][{]\\d+}"
val rawFormatBase10PartPattern = "0[{]\\d+}"
val rawFormatBase26PartPattern = "A[{]\\d+}"
val rawFormatPartWidthPattern = "(?<=[{])\\d+(?=})"

val rawFormatPartsSplitter = DefaultSerialNumberFormatPartSplitter(rawFormatPartPattern.toRegex())
val base10PartConfigConverter = Base10SerialNumberPartConfigConverter(
    rawFormatBase10PartPattern,
    rawFormatPartWidthPattern,
)
val base26PartConfigConverter = Base26SerialNumberPartConfigConverter(
    rawFormatBase26PartPattern,
    rawFormatPartWidthPattern,
)
val staticPartConfigConverter = StaticSerialNumberPartConfigConverter()

val formatConfigConverter = DefaultSerialNumberFormatConfigConverter(
    rawFormatPartsSplitter,
    listOf(
        base10PartConfigConverter,
        base26PartConfigConverter,
        staticPartConfigConverter,  // it's important to have it the last since it accepts everything
    ),
)

val formatConfig1 = formatConfigConverter.convert("A{2}0{3}")    // serial numbers would be "AA001", "AA002", ... "AB001" etc.
val formatConfig2 = formatConfigConverter.convert("0{1}_0{3}")   // serial numbers would be "0_001", "0_002", ... "1_001" etc.
val formatConfig3 = formatConfigConverter.convert("A{1}_A{2}")   // serial numbers would be "A_AB", "A_AC", ... "B_AB" etc.
```
Format of the String templates may be also configured via setting proper regexes.

By default, there's a `A{2}0{3}` format of serial numbers. Which means, the first 2 symbols are base-26 digits
and next 3 symbols are base 10 (decimal) digits.

## Contributing
Contributions to Serial Number Generator are welcome!
To contribute, please fork this repository and submit a pull request with your changes.

## License
Serial Number Generator is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
