package fit;

// Copyright (c) 2002 Cunningham & Cunningham, Inc.
// Released under the terms of the GNU General Public License version 2 or later.

import java.lang.reflect.*;
import java.util.StringTokenizer;

public class TypeAdapter {
    public Object target;
    public Fixture fixture;
    public Field field;
    public Method method;
    public Class<?> type;


    // Factory //////////////////////////////////

    public static TypeAdapter on(Fixture target, Class<?> type) {
        TypeAdapter a = adapterFor(type);
        a.init(target, type);
        return a;
    }

    public static TypeAdapter on(Fixture fixture, Field field) {
        TypeAdapter a = on(fixture, field.getType());
        a.target = fixture;
        a.field = field;
        return a;
    }

    public static TypeAdapter on(Fixture fixture, Method method) {
        TypeAdapter a = on(fixture, method.getReturnType());
        a.target = fixture;
        a.method = method;
        return a;
    }

    public static TypeAdapter adapterFor(Class<?> type) throws UnsupportedOperationException {
        if (type.isPrimitive()) {

            if (type.equals(byte.class)) return new ByteAdapter();
            if (type.equals(short.class)) return new ShortAdapter();
            if (type.equals(int.class)) return new IntAdapter();
            if (type.equals(long.class)) return new LongAdapter();
            if (type.equals(float.class)) return new FloatAdapter();
            if (type.equals(double.class)) return new DoubleAdapter();
            if (type.equals(char.class)) return new CharAdapter();
            if (type.equals(boolean.class)) return new BooleanAdapter();
            throw new UnsupportedOperationException ("can't yet adapt "+type);
        } else {
            if (type.equals(Byte.class)) return new ClassByteAdapter();
            if (type.equals(Short.class)) return new ClassShortAdapter();
            if (type.equals(Integer.class)) return new ClassIntegerAdapter();
            if (type.equals(Long.class)) return new ClassLongAdapter();
            if (type.equals(Float.class)) return new ClassFloatAdapter();
            if (type.equals(Double.class)) return new ClassDoubleAdapter();
            if (type.equals(Character.class)) return new ClassCharacterAdapter();
            if (type.equals(Boolean.class)) return new ClassBooleanAdapter();
            if (type.isArray()) return new ArrayAdapter();
            return new TypeAdapter();
        }
    }


    // Accessors ////////////////////////////////

    protected void init (Fixture fix, Class<?> tp) {
        this.fixture = fix;
        this.type = tp;
    }

    public Object get() throws IllegalAccessException, InvocationTargetException {
        if (field != null)  {return field.get(target);}
        if (method != null) {return invoke();}
        return null;
    }

    public void set(Object value) throws IllegalAccessException {
        field.set(target, value);
    }

    public Object invoke() throws IllegalAccessException, InvocationTargetException {
        Object params[] = {};
        return method.invoke(target, params);
    }

    public Object parse(String s) throws Exception {
        return fixture.parse(s, type);
    }

    public boolean equals(Object a, Object b) {
        if (a==null) {
            return b==null;
        }
        return a.equals(b);
    }

    public String toString(Object o) {
        if (o==null) {
            return "null";
        }
        return o.toString();
    }


    // Subclasses ///////////////////////////////

    static class ByteAdapter extends ClassByteAdapter {
        @Override
		public void set(Object i) throws IllegalAccessException {
            field.setByte(target, ((Byte)i).byteValue());
        }
    }

    static class ClassByteAdapter extends TypeAdapter {
        @Override
		public Object parse(String s) {
            return new Byte(Byte.parseByte(s));
        }
    }

    static class ShortAdapter extends ClassShortAdapter {
        @Override
		public void set(Object i) throws IllegalAccessException {
            field.setShort(target, ((Short)i).shortValue());
        }
    }

    static class ClassShortAdapter extends TypeAdapter {
        @Override
		public Object parse(String s) {
            return new Short(Short.parseShort(s));
        }
    }

    static class IntAdapter extends ClassIntegerAdapter {
        @Override
		public void set(Object i) throws IllegalAccessException {
            field.setInt(target, ((Integer)i).intValue());
        }
    }

    static class ClassIntegerAdapter extends TypeAdapter {
        @Override
		public Object parse(String s) {
            return new Integer(Integer.parseInt(s));
        }
    }

    static class LongAdapter extends ClassLongAdapter {
        public void set(Long i) throws IllegalAccessException {
            field.setLong(target, i.longValue());
        }
    }

    static class ClassLongAdapter extends TypeAdapter {
        @Override
		public Object parse(String s) {
            return new Long(Long.parseLong(s));
        }
    }

    static class FloatAdapter extends ClassFloatAdapter {
        @Override
		public void set(Object i) throws IllegalAccessException {
            field.setFloat(target, ((Number)i).floatValue());
        }
        @Override
		public Object parse(String s) {
            return new Float(Float.parseFloat(s));
        }
    }

    static class ClassFloatAdapter extends TypeAdapter {
        @Override
		public Object parse(String s) {
            return new Float(Float.parseFloat(s));
        }
    }

    static class DoubleAdapter extends ClassDoubleAdapter {
        @Override
		public void set(Object i) throws IllegalAccessException {
            field.setDouble(target, ((Number)i).doubleValue());
        }
        @Override
		public Object parse(String s) {
            return new Double(Double.parseDouble(s));
        }
    }

    static class ClassDoubleAdapter extends TypeAdapter {
        @Override
		public Object parse(String s) {
            return new Double(Double.parseDouble(s));
        }
    }

    static class CharAdapter extends ClassCharacterAdapter {
        @Override
		public void set(Object i) throws IllegalAccessException {
            field.setChar(target, ((Character)i).charValue());
        }
    }

    static class ClassCharacterAdapter extends TypeAdapter {
        @Override
		public Object parse(String s) {
            return new Character(s.charAt(0));
        }
    }

    static class BooleanAdapter extends ClassBooleanAdapter {
        @Override
		public void set(Object i) throws IllegalAccessException {
            field.setBoolean(target, ((Boolean)i).booleanValue());
        }
    }

    static class ClassBooleanAdapter extends TypeAdapter {
        @Override
		public Object parse(String s) {
            return new Boolean(s);
        }
    }

    static class ArrayAdapter extends TypeAdapter {
        Class<?> componentType;
        TypeAdapter componentAdapter;

        @Override
		protected void init(Fixture trg, Class<?> tp) {
            super.init(trg, tp);
            componentType = tp.getComponentType();
            componentAdapter = on(trg, componentType);
        }

        @Override
		public Object parse(String s) throws Exception {
            StringTokenizer t = new StringTokenizer(s, ",");
            Object array = Array.newInstance(componentType, t.countTokens());
            for (int i=0; t.hasMoreTokens(); i++) {
                Array.set(array, i, componentAdapter.parse(t.nextToken().trim()));
            }
            return array;
        }

        @Override
		public String toString(Object o) {
            if (o==null) return "";
            int length = Array.getLength(o);
            StringBuffer b = new StringBuffer(5*length);
            for (int i=0; i<length; i++) {
                b.append(componentAdapter.toString(Array.get(o, i)));
                if (i < (length-1)) {
                    b.append(", ");
                }
            }
            return b.toString();
        }

        @Override
		public boolean equals(Object a, Object b) {
            int length = Array.getLength(a);
            if (length != Array.getLength(b)) return false;
            for (int i=0; i<length; i++) {
                if (!componentAdapter.equals(Array.get(a,i), Array.get(b,i))) return false;
            }
            return true;
        }
    }
}
