import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by ScoutB on 2018/3/13.
 */

public class Login {
    private static String TAG = "LogTAG";
    private User Luser = new User();
    private PrivilegedUser Puser = new PrivilegedUser();
    public Login(User user){
        this.Luser = user;
    }

    public Login(PrivilegedUser Puser){
        this.Puser = Puser;
    }

    private void ClientLogin(String username, String pwd, short privilege){
        switch (privilege){
            case 2:
                EMClient.getInstance().login(username, pwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.i(TAG, "successed login with privilege 2");
                    }
                    @Override
                    public void onError(int i, String s) {
                        Log.i(TAG, "Failed login");
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        Log.i(TAG, "Plz wait.......");
                    }
                });
                break;
            case 1:
                break;
            case 0:
                break;
        }
    }
}
