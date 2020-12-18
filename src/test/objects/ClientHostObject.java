package objects;

import moduleCore.entities.client.ClientHost;

public class ClientHostObject {
    private ClientHost clientHost = new ClientHost();
    private UserObject userObject = new UserObject();
    public ClientHost getClientHost(){
        clientHost.setId(null);
        clientHost.setName("TestName");
        clientHost.setOwner(userObject.getUserObject());
        clientHost.setPublicKey("publickey");
        return clientHost;
    }

}
