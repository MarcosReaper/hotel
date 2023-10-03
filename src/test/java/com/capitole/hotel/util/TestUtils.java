package com.capitole.hotel.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TestUtils {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String searchRequest(String hotelId, String checkIn, String checkOut, List<String> ages) {
        StringBuilder request = new StringBuilder("{\n");
        request.append("    \"hotelId\": \"").append(hotelId).append("\",\n");
        request.append("    \"checkIn\": \"").append(checkIn).append("\",\n");
        request.append("    \"checkOut\": \"").append(checkOut).append("\",\n");
        request.append("    \"ages\": [\n");
        for (String age : ages) {
            request.append("        ").append(age).append(",\n");
        }
        request.deleteCharAt(request.length() - 2);
        request.append("    ]\n");
        request.append("}");
        return request.toString();
    }

    public static String format(final LocalDate date) {
        return date.format(dateFormatter);
    }
}
