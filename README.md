bar-code-wrapper
================

Simple BarCodeGen Wrapper

#### Dependencies

* barcode4j-2.1.jar
* avalon-framework-impl-4.2.0.jar
* avalon-framework-api-4.2.0.jar

#### Default Options

```java
    private String msj = "";
    private MediaType mediaType = MediaType.PNG;
    private CodeBarType codebarType = CodeBarType.CODE_128;
    private int orientation = 0;
    private int resolution = 300; //dpi only in bitmap mode, depends of MediaType value
    private boolean grayscale = false;
```

#### Use

```java
BarCode.from("12345678901234").format(MediaType.PNG).toGrayScale(true).type(CodeBarType.CODE_128).stream();
```

#### MediaTypes
```java
    MediaType.JPEG;
    MediaType.PNG;
    MediaType.GIF;
    MediaType.SVG;
    MediaType.EPS;
    MediaType.BMP;
```

#### CodeBarTypes
```java
    CodeBarType.CODABAR;
    CodeBarType.POSTNET;
    CodeBarType.INTl2of5;
    CodeBarType.EAN_128;
    CodeBarType.ROYAL_MAIL_CBC;
    CodeBarType.EAN_13;
    CodeBarType.ITF_14;
    CodeBarType.DATAMATRIX;
    CodeBarType.CODE_128;
    CodeBarType.PDF417;
    CodeBarType.USPS4CB;
    CodeBarType.UPC_A;
    CodeBarType.UPC_E;
    CodeBarType.EAN_8;
```

#### Samples

###### Codabar
![Alt text](/src/main/java/net/lalotech/barcode/samples/codabar.png)

###### Code 128
![Alt text](/src/main/java/net/lalotech/barcode/samples/code_128.png)

###### EAN-13
![Alt text](/src/main/java/net/lalotech/barcode/samples/ean_13.png)

###### Pdf417
![Alt text](/src/main/java/net/lalotech/barcode/samples/pdf417.png)

###### Upc-a
![Alt text](/src/main/java/net/lalotech/barcode/samples/upc_a.png)

###### Upc-e
![Alt text](/src/main/java/net/lalotech/barcode/samples/upc_e.png)

###### Postnet
![Alt text](/src/main/java/net/lalotech/barcode/samples/postnet.png)

###### Datamatrix
![Alt text](/src/main/java/net/lalotech/barcode/samples/datamatrix.png)

###### Royal-Mail-Cbc
![Alt text](/src/main/java/net/lalotech/barcode/samples/royal_mail_cbc.png)

###### Usps4cb
![Alt text](/src/main/java/net/lalotech/barcode/samples/usps4cb.png)
