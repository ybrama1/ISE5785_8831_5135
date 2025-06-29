package renderer;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Polygon;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjReader {

    /**
     * Reads vertices from an OBJ file and returns them as a list of Point objects.
     *
     * @param filePath the path to the OBJ file
     * @return a list of Point objects representing the vertices
     */
    public List<Point> readVerticesFromOBJ(String filePath) {
        List<Point> vertices = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("v ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        double z = Double.parseDouble(parts[3]);
                        vertices.add(new Point(x, y, z));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return vertices;
    }

    public Map<String,Material> getMaterialsFromMtl(String filePath) {
        // read materials from an MTL file, the key is the name of the material and the value is the Material object
        Map<String,Material> materials = new java.util.HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentMaterialName = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("newmtl ")) {
                    currentMaterialName = line.split("\\s+")[1];
                    materials.put(currentMaterialName, new Material());
                } else if (currentMaterialName != null && line.startsWith("Kd ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        double r = Double.parseDouble(parts[1]);
                        double g = Double.parseDouble(parts[2]);
                        double b = Double.parseDouble(parts[3]);
                        materials.get(currentMaterialName).setKD(new Double3(r, g, b));
                    }
                }
                // KA
                else if (currentMaterialName != null && line.startsWith("Ka ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        double r = Double.parseDouble(parts[1]);
                        double g = Double.parseDouble(parts[2]);
                        double b = Double.parseDouble(parts[3]);
                        materials.get(currentMaterialName).setKA(new Double3(r, g, b));
                    }
                }
                // KS
                else if (currentMaterialName != null && line.startsWith("Ks ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        double r = Double.parseDouble(parts[1]);
                        double g = Double.parseDouble(parts[2]);
                        double b = Double.parseDouble(parts[3]);
                        materials.get(currentMaterialName).setKS(new Double3(r, g, b));
                    }
                }
                // NS
                else if (currentMaterialName != null && line.startsWith("Ns ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        int nShininess = Integer.parseInt(parts[1]);
                        materials.get(currentMaterialName).setShininess(nShininess);
                    }
                }
                // KT
                else if (currentMaterialName != null && line.startsWith("Tr ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        double transparency = Double.parseDouble(parts[1]);
                        materials.get(currentMaterialName).setKT(new Double3(transparency));
                    }
                }
                else if (currentMaterialName != null && line.startsWith("d ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        double transparency = 1 - Double.parseDouble(parts[1]);
                        materials.get(currentMaterialName).setKT(new Double3(transparency));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading MTL file: " + e.getMessage());
        }
        return materials;
    }

    /**
     * Reads polygons from an OBJ file and returns them as a list of Geometry objects.
     * each polygon associated with a Material if available.
     * @param filePath the path to the OBJ file
     * @return a list of Geometry objects representing the polygons
     */
    public List<Geometry> readPolygonsFromOBJ(String filePath) {
        List<Geometry> polygons = new ArrayList<>();
        List<Point> vertices = readVerticesFromOBJ(filePath);


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            //define the map for the materials
            Map<String, Material> materials = new java.util.HashMap<>();
            Material currentMaterial = null;
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                //if line start with 'mtllib' read the materials from the mtl file
                if (line.startsWith("mtllib ")) {
                    //add the prefix of filePath to the mtl file path
                    String mtlFilePath = line.split("\\s+")[1];
                    mtlFilePath = filePath.substring(0, filePath.lastIndexOf('\\') + 1) + mtlFilePath;
                    materials = getMaterialsFromMtl(mtlFilePath);
                }
                //if line starts with 'usemtl' set the current material
                else if (line.startsWith("usemtl ")) {
                    String materialName = line.split("\\s+")[1];
                    currentMaterial = materials.get(materialName);
                    if (currentMaterial == null) {
                        currentMaterial = Material.DEFAULT;
                    }
                }
                else if (line.startsWith("f ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        List<Point> polygonVertices = new ArrayList<>();
                        for (int i = 1; i < parts.length; i++) {
                            int vertexIndex = Integer.parseInt(parts[i].split("/")[0]) - 1;
                            polygonVertices.add(vertices.get(vertexIndex));
                        }
                        try {
                            polygons.add(new Polygon(polygonVertices)
                                    .setMaterial(currentMaterial));
                        } catch(IllegalArgumentException e) {
                            System.err.println("Error creating polygon: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return polygons;
    }

    /**
     * Reads an OBJ file and returns a Geometries object containing all the polygons in the file.
     *
     * @param filePath the path to the OBJ file
     * @return a Geometries object containing all the polygons
     */
    public Geometries getGeometryFromOBJ(String filePath) {
        List<Geometry> polygons = readPolygonsFromOBJ(filePath);
        Geometries geometries = new Geometries();
        geometries.add(polygons);
        return geometries;
    }


}