/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lalotech.barcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import net.lalotech.barcode.exception.BarCodeCreateException;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.apache.log4j.Logger;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.eps.EPSCanvasProvider;
import org.krysalis.barcode4j.output.svg.SVGCanvasProvider;

/**
 *
 * @author Eduardo
 */
public class BarCode {

    private String msj = "";
    private MediaType mediaType = MediaType.PNG;
    private CodeBarType codebarType = CodeBarType.CODE_128;
    private int width = 150;
    private int height = 150;
    private int orientation = 0;
    private int resolution = 300; //dpi
    private boolean grayscale = false;
    private String humaSize = "5pt";
    private Logger log = Logger.getLogger(BarCode.class);

    private BarCode(String msj) {
        this.msj = msj;
    }

    public static BarCode from(String msj) {
        return new BarCode(msj);
    }

    public BarCode format(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public BarCode size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public BarCode type(CodeBarType codeBarType) {
        this.codebarType = codeBarType;
        return this;
    }

    public BarCode toGrayScale(boolean gray) {
        this.grayscale = gray;
        return this;
    }

    public ByteArrayOutputStream stream() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream(4096);

        try {
            Configuration cfg = buildCfg();
            BarcodeUtil util = BarcodeUtil.getInstance();
            BarcodeGenerator gen = util.createBarcodeGenerator(cfg);
            log.info("create stream data");

            if (this.mediaType.toString().equals(MediaType.SVG.toString())) {
                //SVG
                //Create Barcode and render it to SVG
                SVGCanvasProvider svg = new SVGCanvasProvider(false, orientation);
                gen.generateBarcode(svg, this.msj);
                org.w3c.dom.DocumentFragment frag = svg.getDOMFragment();

                //Serialize SVG barcode
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer trans = factory.newTransformer();
                Source src = new javax.xml.transform.dom.DOMSource(frag);
                Result res = new javax.xml.transform.stream.StreamResult(stream);
                trans.transform(src, res);
            } else if (this.mediaType.toString().equals(MediaType.EPS.toString())) {
                EPSCanvasProvider eps = new EPSCanvasProvider(stream, this.orientation);
                gen.generateBarcode(eps, this.msj);
                eps.finish();
            } else {
                if (resolution > 2400) {
                    throw new IllegalArgumentException(
                            "Resolutions above 2400dpi are not allowed");
                }
                if (resolution < 10) {
                    throw new IllegalArgumentException(
                            "Minimum resolution must be 10dpi");
                }
                BitmapCanvasProvider bitmap = new BitmapCanvasProvider(
                        stream,
                        this.mediaType.toString(),
                        this.resolution,
                        (this.grayscale ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_BYTE_BINARY),
                        this.grayscale,
                        this.orientation);

                gen.generateBarcode(bitmap, this.msj);
                bitmap.finish();
            }
        } catch (Exception e) {
            throw new BarCodeCreateException("Can't create barcode",e);
        }
        return stream;
    }
    
    public File file(String path,String name)throws Exception {
        String spath = path +"\\"+ name+ "." + this.mediaType.toString().split("/")[1];
        log.info("location file: " + spath);
        return buildFile(new File(spath));
    }

    public File file(String path) throws Exception {
        String spath = path +"\\"+ "code_" + System.currentTimeMillis() + "." + this.mediaType.toString().split("/")[1];
        log.info("location file: " + spath);        
        return buildFile(new File(spath));
    }

    public File file() throws Exception {
        String spath = "code_" + System.currentTimeMillis() + "." + this.mediaType.toString().split("/")[1];
        log.info("location file: " + spath);        
        return buildFile(new File(spath));
    }

    private File buildFile(File f) throws Exception {
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(stream().toByteArray());
        fos.close();
        return f;
    }

    private Configuration buildCfg() {
        DefaultConfiguration cfg = new DefaultConfiguration("barcode");
        //Get type
        String type = this.codebarType.toString();

        DefaultConfiguration child = new DefaultConfiguration(type);
        cfg.addChild(child);
        //Get additional attributes
        DefaultConfiguration attr;
        //height
        //attr = new DefaultConfiguration("height");
        //attr.setValue(this.height);
        //child.addChild(attr);

        //module width
        //attr = new DefaultConfiguration("module-width");
        //attr.setValue(this.width);
        //child.addChild(attr);
        //String wideFactor = request.getParameter(BARCODE_WIDE_FACTOR);
        String wideFactor = null;
        if (wideFactor != null) {
            attr = new DefaultConfiguration("wide-factor");
            attr.setValue(wideFactor);
            child.addChild(attr);
        }
        //String quietZone = request.getParameter(BARCODE_QUIET_ZONE);
        String quietZone = null;
        if (quietZone != null) {
            attr = new DefaultConfiguration("quiet-zone");
            if (quietZone.startsWith("disable")) {
                attr.setAttribute("enabled", "false");
            } else {
                attr.setValue(quietZone);
            }
            child.addChild(attr);
        }

        // creating human readable configuration according to the new Barcode Element Mappings
        // where the human-readable has children for font name, font size, placement and
        // custom pattern.
        /*String humanReadablePosition = request.getParameter(BARCODE_HUMAN_READABLE_POS);
         String pattern = request.getParameter(BARCODE_HUMAN_READABLE_PATTERN);
         String humanReadableSize = request.getParameter(BARCODE_HUMAN_READABLE_SIZE);
         String humanReadableFont = request.getParameter(BARCODE_HUMAN_READABLE_FONT);*/
        String humanReadablePosition = null;
        String pattern = null;
        String humanReadableSize = null;
        String humanReadableFont = null;

        if (!((humanReadablePosition == null)
                && (pattern == null)
                && (humanReadableSize == null)
                && (humanReadableFont == null))) {
            attr = new DefaultConfiguration("human-readable");

            DefaultConfiguration subAttr;
            if (pattern != null) {
                subAttr = new DefaultConfiguration("pattern");
                subAttr.setValue(pattern);
                attr.addChild(subAttr);
            }
            if (humanReadableSize != null) {
                subAttr = new DefaultConfiguration("font-size");
                subAttr.setValue(humanReadableSize);
                attr.addChild(subAttr);
            }
            if (humanReadableFont != null) {
                subAttr = new DefaultConfiguration("font-name");
                subAttr.setValue(humanReadableFont);
                attr.addChild(subAttr);
            }
            if (humanReadablePosition != null) {
                subAttr = new DefaultConfiguration("placement");
                subAttr.setValue(humanReadablePosition);
                attr.addChild(subAttr);
            }
            child.addChild(attr);
        }
        return cfg;
    }
}
