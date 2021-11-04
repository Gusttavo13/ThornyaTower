package club.thornya.thornyatower.Database;


import club.thornya.thornyatower.ThornyaTower;

import java.sql.*;

public class SQLite {

    String url = null;

    public SQLite(){
        url = "jdbc:sqlite:" + ThornyaTower.TT.getDataFolder() + "/data.db";
        createTableRoom();
        createTableParty();
        createTableLog();
        createClanLevelMax();

    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    public void createTableRoom() {
        String sql = "CREATE TABLE IF NOT EXISTS rooms (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	room string NOT NULL,\n"
                + "	locationmob string,\n"
                + "	spawn string\n"
                + ");";

        try (Connection conn =  connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connect().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void createClanLevelMax() {
        String sql = "CREATE TABLE IF NOT EXISTS clanmax (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	clan string NOT NULL,\n"
                + "	level integer\n"
                + ");";

        try (Connection conn =  connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connect().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void createTableParty() {
        String sql = "CREATE TABLE IF NOT EXISTS party (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	clan string NOT NULL,\n"
                + "	players string,\n"
                + "	room string\n"
                + ");";

        try (Connection conn =  connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connect().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void createTableLog() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	player string NOT NULL,\n"
                + "	clan string NOT NULL,\n"
                + "	mobskilled integer,\n"
                + "	bosseskilled integer,\n"
                + "	deaths integer\n"
                + ");";

        try (Connection conn =  connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connect().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void criarValorPrefeitura(Float valor) {
        String sql = "INSERT INTO Taxas (valor) VALUES(?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, valor);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public Double getValuePrefeitura(){
        Double valor = 0.0;
        String sql = "SELECT valor FROM Taxas";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                valor = rs.getDouble("valor");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return valor;
    }

    public void updateClanLevelMax(String clanName, Integer level) {
        String sql = "INSERT OR IGNORE INTO clanmax (clan, level) VALUES ('$CLAN', $LEVEL); UPDATE clanmax SET level = $LEVEL WHERE clan='$CLAN';"
                .replace("$CLAN", clanName)
                .replace("$LEVEL", String.valueOf(level));

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}