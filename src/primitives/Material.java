package primitives;

public class Material {
    /*** The ambient light coefficient*/
    public Double3 kA = Double3.ONE;
    /*** The diffuse light coefficient*/
    public Double3 kD = Double3.ZERO;
    /*** The specular light coefficient*/
    public Double3 kS = Double3.ZERO;
    /*** The shininess coefficient*/
    public int nSh = 0;
    /*** The transparency coefficient*/
    public Double3 kT = Double3.ZERO;
    /*** The reflection coefficient*/
    public Double3 kR = Double3.ZERO;

    public Material setKA(Double3 kA) {
        this.kA = kA;
        return this;
    }
    public Material setKA(double kA) {
        this.kA = new Double3(kA);
        return this;
    }
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }
    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }
    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    public Material setShininess(int nSh) {
        this.nSh = nSh;
        return this;
    }
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }
    public Material setKT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }
    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

}
