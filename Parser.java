package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.FileOutputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Document internalData;
    private FlatInfo flat;
    List<FlatInfo> flatsList = new ArrayList<>();
    String linkStr;

    public Parser() {
        flatsList = new ArrayList<>();
    }

    public void setInternalData(Document internalData) {
        this.internalData = internalData;
    }

    public void setFlat(FlatInfo flat) {
        this.flat = flat;
    }

    public void setLinkStr(int page) {
        this.linkStr = "https://www.cian.ru/cat.php?deal_type=sale&demolished_in_moscow_programm=0&engine_version=2&foot_min=45&house_material%5B0%5D=1&house_material%5B1%5D=2&house_material%5B2%5D=3&house_material%5B3%5D=4&house_material%5B4%5D=6&house_material%5B5%5D=8&minsu_s=1&offer_type=flat&only_foot=2&p=" + page + "&parking_type%5B0%5D=2&parking_type%5B1%5D=3&parking_type%5B2%5D=4&region=1&room2=1";
    }

    public String getLinkStr() {
        return linkStr;
    }

    public List<FlatInfo> getFlatsList() {
        return flatsList;
    }

    public void goThrowPages() throws IOException, InterruptedException {
        for (int page = 1; page < 15; page++){
            setLinkStr(page);
            collectLinksFromOnePage();
        }
    }

    public void collectLinksFromOnePage() throws IOException {
        Parser parser = new Parser();
        Document doc = Jsoup.connect(getLinkStr()).get();
        Elements postTitleElements = doc.getElementsByAttributeValue("class", "_93444fe79c--link--eoxce");

        String tmpLink = "";
        for (Element el : postTitleElements) {
            if (tmpLink.equals(el.attr("href")))
                continue;
            tmpLink = el.attr("href");
            FlatInfo flat = new FlatInfo();
            parser.setFlat(flat);
            flat.setLinkDebug(el.attr("href"));
            flatsList.add(flat);

            parser.setInternalData(Jsoup.connect(tmpLink).get());
            parser.parseTimeToMetro();
            parser.parseFlatName();
            //parser.parseFlatPrice();
            parser.parseCommonArea();
            parser.parseCommonKitchenArea();
            parser.parseRoomHeight();
            parser.parseFloor();
            parser.parseBuildYear();
            parser.parseSquareMeterPrice();
            parser.parseFlatType();
            parser.parseBathroomsQuantity();
            parser.parseHouseType();
            parser.parseParkingType();
            parser.parseBalconyQuantity();
            parser.parseRepairType();
        }

    }

    public void parseTimeToMetro() {
        try {
            try {
                Element divElement = internalData.getElementsByAttributeValue("data-name", "UndergroundItem").first().child(2);
                String textContent = divElement.text();
                int timeToMetro = Integer.parseInt(textContent.replaceAll("\\D", ""));
                flat.setTimeToMetro(timeToMetro);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseFlatName() {
        try {
            Element divElement = internalData.getElementsByAttributeValue("class", "a10a3f92e9--link--A5SdC").first();
            String flatName = divElement.select("a").first().text();
            flat.setFlatName(flatName);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseFlatPrice() {
        try {
            Element divElement = internalData.getElementsByAttributeValue("data-name", "PriceInfo").first();
            String flatPrice = divElement.select("span").first().text().replaceAll("\\D", "");
            flat.setFlatPrice(flatPrice);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseCommonArea() {
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
            for (Element el : divElement) {
                if (el.text().contains("Общая площадь")) {
                    String commonArea = el.text().replaceAll("Общая площадь", "").replaceAll(",", ".");
                    float area = Float.parseFloat(commonArea);
                    flat.setCommonArea(area);
                    System.out.println(commonArea);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseCommonKitchenArea() {
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
            for (Element el : divElement) {
                if (el.text().contains("Площадь кухни")) {
                    String commonKitchenArea = el.text().replaceAll("Площадь кухни", "").replaceAll(",", ".");
                    float area = Float.parseFloat(commonKitchenArea);
                    flat.setCommonKitchen(area);
                    System.out.println(commonKitchenArea);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseRoomHeight() {
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
            for (Element el : divElement) {
                if (el.text().contains("Высота потолков")) {
                    String roomHeight = el.text().replaceAll("Высота потолков", "").replaceFirst("..$", "").replaceAll(",", ".");
                    float height = Float.parseFloat(roomHeight);
                    flat.setRoomHeight(height);
                    System.out.println(roomHeight);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseFloor() {
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "ObjectFactoidsItem");
            for (Element el : divElement) {
                if (el.text().contains("Этаж")) {
                    String floor = el.text().replaceAll("Этаж", "");
                    floor = floor.substring(0, floor.indexOf(" "));
                    flat.setFloor(floor);
                    System.out.println(floor);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseBuildYear() {
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "ObjectFactoidsItem");
            for (Element el : divElement) {
                if (el.text().contains("Год постройки")) {
                    System.out.println(flat.getLinkDebug());
                    String year = el.text().replaceAll("Год постройки", "");
                    int houseAge = Year.now().getValue() - Integer.parseInt(year);
                    flat.setHouseAge(houseAge);
                    System.out.println(year);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseSquareMeterPrice() {
        try {
            Element divElement = internalData.getElementsByAttributeValue("data-name", "OfferFactItem").first().select("span").get(1);
            String pricePerMeter = divElement.text().replaceAll("\\D", "");
            System.out.println(pricePerMeter);
            flat.setOneSquareMeterPrice(pricePerMeter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseFlatType() { //однотипно
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
            for (Element el : divElement) {
                if (el.text().contains("Тип жилья")) {
                    String flatType = el.text().replaceAll("Тип жилья", "");
                    flat.setFlatType(flatType);
                    System.out.println(flatType);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseBathroomsQuantity() {
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
            for (Element el : divElement) {
                if (el.text().contains("Санузел")) {
                    String baths = el.text().replaceAll("Санузел", "").replaceAll(" совмещенный", "").replaceAll(" раздельный", "").replaceAll(",", "").replaceAll(" совмещенных", "").replaceAll(" раздельных", "");
                    String[] numberOfBathrooms = baths.split(" ");
                    int bathsQuantity = 0;
                    if (numberOfBathrooms.length > 1){
                        bathsQuantity = Integer.parseInt(numberOfBathrooms[0]) + Integer.parseInt(numberOfBathrooms[1]);
                        flat.setBathroomsQuantity(bathsQuantity);
                    } else if (numberOfBathrooms.length == 1) {
                        bathsQuantity = Integer.parseInt(numberOfBathrooms[0]);
                        flat.setBathroomsQuantity(bathsQuantity);
                    }
                    System.out.println(baths);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseHouseType() { //однотипно
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
            for (Element el : divElement) {
                if (el.text().contains("Тип дома")) {
                    String houseType = el.text().replaceAll("Тип дома", "");
                    flat.setHouseType(houseType);
                    System.out.println(houseType);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseParkingType() {
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
            for (Element el : divElement) {
                if (el.text().contains("Парковка")) {
                    String parking = el.text().replaceAll("Парковка", "");
                    flat.setParkingType(parking);
                    System.out.println(parking);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseRepairType() {
        try {
            try {
                Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
                for (Element el : divElement) {
                    if (el.text().contains("Ремонт")) {
                        String repair = el.text().replaceAll("Ремонт", "");
                        flat.setTypeOfRepair(repair);
                        System.out.println(repair);
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void parseBalconyQuantity() {
        try {
            Elements divElement = internalData.getElementsByAttributeValue("data-name", "OfferSummaryInfoItem");
            for (Element el : divElement) {
                if (el.text().contains("Балкон/лоджия")) {
                    String balcony = el.text().replaceAll("Балкон/лоджия", "").replaceAll(" балкона", "").replaceAll(" лоджия", "").replaceAll(" балкон", "").replaceAll(",", "").replaceAll("лоджии", "");

                    String[] numberOfBalconies = balcony.split(" ");
                    int balconiesQuantity = 0;
                    if (numberOfBalconies.length > 1){
                        balconiesQuantity = Integer.parseInt(numberOfBalconies[0]) + Integer.parseInt(numberOfBalconies[1]);
                        flat.setBalconyQuantity(balconiesQuantity);
                    } else if (numberOfBalconies.length == 1) {
                        balconiesQuantity = Integer.parseInt(numberOfBalconies[0]);
                        flat.setBalconyQuantity(balconiesQuantity);
                    }
                    System.out.println(balconiesQuantity);
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



    public static void exportToExcel(List<FlatInfo> flatsList, String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("FlatInfo");

            // Создаем заголовки столбцов
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Ссылка");
            headerRow.createCell(1).setCellValue("Название квартиры");
            headerRow.createCell(2).setCellValue("Время до метро");
//            headerRow.createCell(3).setCellValue("Цена");
            headerRow.createCell(4).setCellValue("Площадь");
            headerRow.createCell(5).setCellValue("Площадь кухни");
            headerRow.createCell(6).setCellValue("Этаж");
            headerRow.createCell(7).setCellValue("Год постройки");
            headerRow.createCell(8).setCellValue("Высота потолков");
            headerRow.createCell(9).setCellValue("Цена за 1 кв. метр");
            headerRow.createCell(10).setCellValue("Тип квартиры");
            headerRow.createCell(11).setCellValue("Количество санузлов");
            headerRow.createCell(12).setCellValue("Тип дома");
            headerRow.createCell(13).setCellValue("Тип парковки");
            headerRow.createCell(14).setCellValue("Количество балконов");
            headerRow.createCell(15).setCellValue("Тип ремонта");

            int rowNum = 1;
            for (FlatInfo flat : flatsList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(flat.getLinkDebug());
                row.createCell(1).setCellValue(flat.getFlatName());
                row.createCell(2).setCellValue(flat.getTimeToMetro());
//                row.createCell(3).setCellValue(flat.getFlatPrice());
                row.createCell(4).setCellValue(flat.getCommonArea());
                row.createCell(5).setCellValue(flat.getCommonKitchen());
                row.createCell(6).setCellValue(flat.getFloor());
                row.createCell(7).setCellValue(flat.getHouseAge());
                row.createCell(8).setCellValue(flat.getRoomHeight());
                row.createCell(9).setCellValue(flat.getOneSquareMeterPrice());

                row.createCell(10).setCellValue(flat.getFlatType());
                row.createCell(11).setCellValue(flat.getBathroomsQuantity());
                row.createCell(12).setCellValue(flat.getHouseType());
                row.createCell(13).setCellValue(flat.getParkingType());
                row.createCell(14).setCellValue(flat.getBalconyQuantity());
                row.createCell(15).setCellValue(flat.getTypeOfRepair());
            }

            // Записываем данные в файл
            try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Parser parserList = new Parser();
        parserList.goThrowPages();

        String outputPath = "output_v2.xlsx";
        exportToExcel(parserList.getFlatsList(), outputPath);
        System.out.println("Данные экспортированы в Excel.");
    }

}