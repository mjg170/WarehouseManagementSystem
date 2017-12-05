import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SaveObject {


    public void saveObject(Connection conn,Object bc) throws Exception {
        byte[] byteArray = null;
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(bc);
            oos.flush();
            oos.close();
            bos.close();
            byteArray = bos.toByteArray();


            String sql = "Update blockchain set block = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, byteArray);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public blockchain getBlockchain(Connection conn) throws Exception {
        blockchain bc = null;
        String sql = "select * from blockchain";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ByteArrayInputStream bais;

            ObjectInputStream ins;

            try {
                bais = new ByteArrayInputStream(rs.getBytes("block"));
                ins = new ObjectInputStream(bais);
                bc = (blockchain)ins.readObject();
                System.out.println("Object in value ::");
                ins.close();
                return bc;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return bc;
    }
}

