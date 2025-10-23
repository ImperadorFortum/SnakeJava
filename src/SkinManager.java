public class SkinManager {
    private static String selectedSkin = "Verde.png"; // Skin padrão

    public static String getSelectedSkin() {
        return selectedSkin;
    }

    public static void setSelectedSkin(String skinFileName) {
        selectedSkin = skinFileName;
    }
}

