/* eslint-disable */
import React, { PureComponent } from 'react';
import { AxiosError } from 'axios';
import { useState, useEffect } from 'react';
import customAxios from 'utils/api/axios';
import { useParams } from 'react-router-dom';
import { getItemsData } from 'utils/api/api';

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';

const data = [
  {
    transDate: '1',
    transprice: 2400,
  },
  {
    transDate: '2',
    transprice: 1398,
  },
  {
    transDate: '3',
    transprice: 970,
  },
  {
    transDate: '4',
    transprice: 3908,
  },
  {
    transDate: '5',
    transprice: 4800,
  },
];

export default class Example extends PureComponent {
  static demoUrl = 'https://codesandbox.io/s/simple-line-chart-kec3v';
  
  

  render() {
    return (
      <ResponsiveContainer width="100%" height="100%">
        <LineChart
          width={500}
          height={300}
          data={data}
          margin={{
            top: 8,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="transDate" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line
            type="monotone"
            dataKey="transprice"
            stroke="#3ab712"
            activeDot={{ r: 8 }}
          />
        </LineChart>
      </ResponsiveContainer>
    );
  }
};