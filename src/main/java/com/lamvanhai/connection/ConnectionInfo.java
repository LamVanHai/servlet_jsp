package com.lamvanhai.connection;

public class ConnectionInfo {
    private String url;
    private String user;
    private String password;
    private String driverClassName;

    private ConnectionInfo(ConnectionInfoBuilder connectionInfoBuilder) {
        this.url = connectionInfoBuilder.url;
        this.user = connectionInfoBuilder.user;
        this.password = connectionInfoBuilder.password;
        this.driverClassName = connectionInfoBuilder.driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public static class ConnectionInfoBuilder {
        private String url;
        private String user;
        private String password;
        private String driverClassName;

        public ConnectionInfoBuilder url(String url) {
            this.url = url;

            return this;
        }

        public ConnectionInfoBuilder user(String user) {
            this.user = user;

            return this;
        }

        public ConnectionInfoBuilder password(String password) {
            this.password = password;

            return this;
        }

        public ConnectionInfoBuilder driverClassName(String driverClassName) {
            this.driverClassName = driverClassName;

            return this;
        }

        public ConnectionInfo build() {
            return new ConnectionInfo(this);
        }
    }
}
