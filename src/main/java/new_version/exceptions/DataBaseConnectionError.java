package new_version.exceptions;

public class DataBaseConnectionError extends RuntimeException {
    public DataBaseConnectionError() {
        super("Проблема с подключением к базе данных.\nПопробуйте снова");
    }
}
