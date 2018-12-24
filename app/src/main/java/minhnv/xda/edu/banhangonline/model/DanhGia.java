package minhnv.xda.edu.banhangonline.model;

/**
 * Created by MinhNguyen on 12/26/2017.
 */

public class DanhGia {
    public int id;
    public String tennguoidanhgia;
    public String noidung;
    public String tieude;
    public int sao;

    public DanhGia() {
    }

    public DanhGia(int id, String tennguoidanhgia, String noidung, String tieude, int sao) {
        this.id = id;
        this.tennguoidanhgia = tennguoidanhgia;
        this.noidung = noidung;
        this.tieude = tieude;
        this.sao = sao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTennguoidanhgia() {
        return tennguoidanhgia;
    }

    public void setTennguoidanhgia(String tennguoidanhgia) {
        this.tennguoidanhgia = tennguoidanhgia;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public int getSao() {
        return sao;
    }

    public void setSao(int sao) {
        this.sao = sao;
    }

    @Override
    public String toString() {
        return "DanhGia{" +
                "id=" + id +
                ", tennguoidanhgia='" + tennguoidanhgia + '\'' +
                ", noidung='" + noidung + '\'' +
                ", tieude='" + tieude + '\'' +
                ", sao=" + sao +
                '}';
    }
}
