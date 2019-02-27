package new_version.users.admin.admin_commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class Command {
    protected Message message;

    public Command(Message message) {
        this.message = message;
    }
}
