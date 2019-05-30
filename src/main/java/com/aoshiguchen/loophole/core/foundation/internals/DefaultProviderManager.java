package com.aoshiguchen.loophole.core.foundation.internals;

import com.aoshiguchen.loophole.core.foundation.internals.provider.DefaultDingProvider;
import com.aoshiguchen.loophole.core.foundation.internals.provider.DefaultIpv4Provider;
import com.aoshiguchen.loophole.core.foundation.internals.provider.DefaultNetworkProvider;
import com.aoshiguchen.loophole.core.foundation.spi.ProviderManager;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Provider;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultProviderManager implements ProviderManager {
  private Map<Class<? extends Provider>, Provider> m_providers = new LinkedHashMap<>();

  public DefaultProviderManager() {
    Provider networkProvider = new DefaultNetworkProvider();
    networkProvider.initialize();
    register(networkProvider);

    DefaultIpv4Provider ipv4Provider = new DefaultIpv4Provider();
    ipv4Provider.initialize();
    register(ipv4Provider);

    DefaultDingProvider defaultDingProvider = new DefaultDingProvider();
    defaultDingProvider.initialize();
    register(defaultDingProvider);
  }

  public synchronized void register(Provider provider) {
    m_providers.put(provider.getType(), provider);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Provider> T provider(Class<T> clazz) {
    Provider provider = m_providers.get(clazz);

    if (provider != null) {
      return (T) provider;
    } else {
      return (T) NullProviderManager.provider;
    }
  }

  @Override
  public String getProperty(String name, String defaultValue) {
    for (Provider provider : m_providers.values()) {
      String value = provider.getProperty(name, null);

      if (value != null) {
        return value;
      }
    }

    return defaultValue;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(512);
    if (null != m_providers) {
      for (Map.Entry<Class<? extends Provider>, Provider> entry : m_providers.entrySet()) {
        sb.append(entry.getValue()).append("\n");
      }
    }
    sb.append("(DefaultProviderManager)").append("\n");
    return sb.toString();
  }
}
