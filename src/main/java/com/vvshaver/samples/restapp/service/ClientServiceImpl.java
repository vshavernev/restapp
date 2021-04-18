package com.vvshaver.samples.restapp.service;

import com.vvshaver.samples.restapp.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClientServiceImpl implements ClientService {

    // Хранилище клиентов
    private static final Map<Integer, Client> CLIENT_REPOSITORY_MAP = new HashMap<>();

    // Переменная для генерации ID клиента
    private static final AtomicInteger CLIENT_ID_HOLDER = new AtomicInteger();

    @Override
    public void create(Client client) {
        final int clientId = CLIENT_ID_HOLDER.incrementAndGet();
        Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

        client.setId(clientId);
        CLIENT_REPOSITORY_MAP.put(clientId, client);
        logger.info(client.toString());
    }

    @Override
    public List<Client> readAll() {
        return new ArrayList<>(CLIENT_REPOSITORY_MAP.values());
    }

    @Override
    public List<Client> readByName(String name) {
        ArrayList<Client> filteredClientList = new ArrayList<Client>(CLIENT_REPOSITORY_MAP.size());

        CLIENT_REPOSITORY_MAP.forEach((key, client) -> {
            if (client.getName().contains(name))
                filteredClientList.add(client);
        });

        return filteredClientList;
    }

    @Override
    public Client read(int id) {
        return CLIENT_REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(Client client, int id) {
        if (CLIENT_REPOSITORY_MAP.containsKey(id)) {
            client.setId(id);
            CLIENT_REPOSITORY_MAP.put(id, client);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        return CLIENT_REPOSITORY_MAP.remove(id) != null;
    }
}
