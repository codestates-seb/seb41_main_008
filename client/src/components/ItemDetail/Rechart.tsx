/* eslint-disable */
import React, { PureComponent , } from 'react';
import { AxiosError } from 'axios';
import { useState, useEffect } from 'react';
import customAxios from 'utils/api/axios';
import { useParams } from 'react-router-dom';
import { getItemsData } from 'utils/api/api';
import {ItemProps} from '../../components/ItemDetail/ItemDetail';

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


export default function Example({ data }: { data: ItemProps | undefined }) {
  return (
    <ResponsiveContainer width="100%" height="100%">
      <LineChart
        width={500}
        height={300}
        data={data?.priceHistory}
        margin={{
          top: 8,
          right: 30,
          bottom: 1,
          left:2,
        }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="transDate" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Line
          type="monotone"
          dataKey="transPrice"
          stroke="#3ab712"
          activeDot={{ r: 8 }}
        />
      </LineChart>
    </ResponsiveContainer>
  );
};