package com.sensor.sellCabinet.service.impl;

class swapClass
{
	public long a;
	public long b;
}

class payDat_t
{
	public char[] buf8 = new char[8];	
	private swapClass buf32 = new swapClass();
	
	public swapClass GetBuf32()
	{		
		buf32.a = (buf8[0] & 0xFF) 
                + ((buf8[1] & 0xFF) << 8)
                + ((buf8[2] & 0xFF) << 16)
                + ((buf8[3] & 0xFF) << 24);
		buf32.b = (buf8[4] & 0xFF) 
                + ((buf8[5] & 0xFF) << 8)
                + ((buf8[6] & 0xFF) << 16)
                + ((buf8[7] & 0xFF) << 24);
		
		return buf32;
	}
	
	public char[] GetBuf8()
	{
		buf8[0] = (char)((buf32.a >>> 0) & 0xFF);
		buf8[1] = (char)((buf32.a >>> 8) & 0xFF);
		buf8[2] = (char)((buf32.a >>> 16) & 0xFF);
		buf8[3] = (char)((buf32.a >>> 24) & 0xFF);
		
		buf8[4] = (char)((buf32.b >>> 0) & 0xFF);
		buf8[5] = (char)((buf32.b >>> 8) & 0xFF);
		buf8[6] = (char)((buf32.b >>> 16) & 0xFF);
		buf8[7] = (char)((buf32.b >>> 24) & 0xFF);
		
		return buf8;
	}
}

public class PayCrypt {


	private static char[] g_pCryptKey = 
	{
			0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x088
	};
	
	//Blowfish P����
	public static final long[] _BLOWFISH_PArray = 
	{
		0x2D45BDCF,0xFBC30D68,0xA1E2A893,
		0xF3049A77,0x31734737,0x36001515,
		0xCD46BD89,0xD8C6CB62,0x3BBF9763,
		0x45535FB6,0x98606C2E,0xB673B54B,
		0x41A670D5,0xB3536F1B,0x041F8B1D,
		0xCC2CBCE8,0x16459B0D,0xBC88F407
	};	
	
	public static final char[] _BLOWFISH_SBox =
	{
		3, 11, 8,  6, 13, 1, 0, 15,
		10, 5, 7, 14,  9, 4, 12, 2	
	};


	static long _BLOWFISH_f(long x) 
	{	
	   int i, j;
	   long y = 0;
	   
	   for( i = 0; i < 5; i ++ )
	   {
		   j = (int)(x & 0x0F);
		   y = y << 4;
		   y |= _BLOWFISH_SBox[j];
		   x = x >>> 4;
	   }
	   
	   return y;
	}

	static void _BLOWFISH_swap(swapClass ab)
	{
		long tmp;
		
		tmp = ab.a;
		ab.a = ab.b;
		ab.b = tmp;
	}


	//加密函数
	static void encrypt(swapClass lr) 
	{
		int i;
		
		for (i = 0 ; i < 16 ; i += 2) 
		{
			lr.a ^= _BLOWFISH_PArray[i];
			lr.b ^= _BLOWFISH_f(lr.a);
			lr.b ^= _BLOWFISH_PArray[i+1];
			lr.a ^= _BLOWFISH_f(lr.b);
		}
		
		lr.a ^= _BLOWFISH_PArray[16];
		lr.b ^= _BLOWFISH_PArray[17];
		_BLOWFISH_swap(lr);
	}

	//解密函数
	static void decrypt(swapClass lr)
	{
		int i;
		
		for (i = 16 ; i > 0 ; i -= 2) 
		{
			lr.a ^= _BLOWFISH_PArray[i+1];
			lr.b ^= _BLOWFISH_f(lr.a);
			lr.b ^= _BLOWFISH_PArray[i];
			lr.a ^= _BLOWFISH_f(lr.b);
		}
		
		lr.a ^= _BLOWFISH_PArray[1];
		lr.b ^= _BLOWFISH_PArray[0];
		_BLOWFISH_swap(lr);
	}

	//加密函数
	void pCrypt_Encrypt(char[] pbuf)
	{
		payDat_t payDat = new payDat_t();
		int i;
		
		for(i = 0; i < 8; i++)
		{
			payDat.buf8[i] = (char)(g_pCryptKey[i] ^ pbuf[i]);
		}	
		
		encrypt(payDat.GetBuf32());
		payDat.GetBuf8();
		
		for(i = 0; i < 8; i++)
		{
			pbuf[i] = (char)(g_pCryptKey[i] ^ payDat.buf8[i]);
		}	
	}
	
	//解密函数
	void pCrypt_Decrypt(char[] pbuf)
	{
		payDat_t payDat = new payDat_t();
		int i;
		
		for(i = 0; i < 8; i++)
		{
			payDat.buf8[i] = (char)(g_pCryptKey[i] ^ pbuf[i]);
		}
		
		decrypt(payDat.GetBuf32());
		payDat.GetBuf8();
		
		for(i = 0; i < 8; i++)
		{
			pbuf[i] = (char)(g_pCryptKey[i] ^ payDat.buf8[i]);
		}
	}

}
