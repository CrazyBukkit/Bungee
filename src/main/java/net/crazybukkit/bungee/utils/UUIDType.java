package net.crazybukkit.bungee.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by tebbh on 24.02.2017.
 */
public class UUIDType
        extends TypeAdapter<UUID> {

@Override
public void write(JsonWriter out, UUID value) throws IOException {
        out.value(fromUUID(value));
        }

@Override
public UUID read(JsonReader in) throws IOException {
        return fromString(in.nextString());
        }

public String fromUUID(UUID value) {
        return value.toString().replace("-", "");
        }

public UUID fromString(String input) {
        return UUID.fromString(input.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        }
        }